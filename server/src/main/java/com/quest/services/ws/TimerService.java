package com.quest.services.ws;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.quest.config.websocket.WsDestinations;
import com.quest.dto.ws.Game.EngineStateDTO;
import com.quest.dto.ws.Game.TimerDTO;
import com.quest.engine.core.GameEngine;
import com.quest.engine.managers.GameSessionManager;

import jakarta.annotation.PreDestroy;

@Service
public class TimerService {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    private final Map<TimerKey, TimerHandles> timerTasks = new ConcurrentHashMap<>();

    private final GameSessionManager sessionManager;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TimerService(GameSessionManager sessionManager,
            SimpMessagingTemplate messagingTemplate) {
        this.sessionManager = sessionManager;
        this.messagingTemplate = messagingTemplate;
    }

    private enum TimerType {
        TURN(GameEngine::forceSkipTurn),
        QUESTION(GameEngine::forceFailQuestion);

        private final BiConsumer<GameEngine, Long> onTimeoutAction;

        TimerType(BiConsumer<GameEngine, Long> onTimeoutAction) {
            this.onTimeoutAction = onTimeoutAction;
        }
    }

    private record TimerKey(String sessionId, Long playerId, TimerType type) { }

    private static class TimerHandles {
        ScheduledFuture<?> timeoutFuture;
    }

    private void startTimer(String sessionId, Long playerId, int timeoutSeconds, TimerType type) {
        TimerType oppositeType = type == TimerType.TURN ? TimerType.QUESTION : TimerType.TURN;

        TimerKey key = new TimerKey(sessionId, playerId, type);
        TimerKey oppositeKey = new TimerKey(sessionId, playerId, oppositeType);

        cancelTimerInternal(oppositeKey);
        cancelTimerInternal(key);

        String destination = String.format(WsDestinations.TIMER_TICK, sessionId);
        messagingTemplate.convertAndSend(destination, new TimerDTO(playerId, timeoutSeconds, type.name()));

        ScheduledFuture<?> timeout = scheduler.schedule(() -> {
            GameEngine engine = sessionManager.getEngine(sessionId);
            type.onTimeoutAction.accept(engine, playerId);

            String stateDest = String.format(WsDestinations.GAME_STATE, sessionId);
            messagingTemplate.convertAndSend(
                    stateDest,
                    EngineStateDTO.from(sessionId, engine)
            );

            if (!engine.isFinished()) {
                Long nextPlayer = engine.getTurnManager().getCurrentPlayerId();
                startTimer(sessionId, nextPlayer, timeoutSeconds, TimerType.TURN);
            }
        }, timeoutSeconds, TimeUnit.SECONDS);

        TimerHandles handles = new TimerHandles();
        handles.timeoutFuture = timeout;
        timerTasks.put(key, handles);
    }

    private void cancelTimerInternal(TimerKey key) {
        TimerHandles handles = timerTasks.remove(key);
        if (handles != null && handles.timeoutFuture != null && !handles.timeoutFuture.isDone())
            handles.timeoutFuture.cancel(false);
    }

    public void cancelAllTimersForSession(String sessionId) {
        timerTasks.keySet().stream()
                .filter(key -> key.sessionId().equals(sessionId))
                .forEach(this::cancelTimerInternal);
    }

    public void startTurnTimer(String sessionId, Long playerId, int timeoutSeconds) {
        startTimer(sessionId, playerId, timeoutSeconds, TimerType.TURN);
    }

    public void cancelTurnTimer(String sessionId, Long playerId) {
        cancelTimerInternal(new TimerKey(sessionId, playerId, TimerType.TURN));
    }

    public void startQuestionTimer(String sessionId, Long playerId, int timeoutSeconds) {
        startTimer(sessionId, playerId, timeoutSeconds, TimerType.QUESTION);
    }

    public void cancelQuestionTimer(String sessionId, Long playerId) {
        cancelTimerInternal(new TimerKey(sessionId, playerId, TimerType.QUESTION));
    }

    @PreDestroy
    public void shutdownScheduler() {
        scheduler.shutdownNow();
    }
}

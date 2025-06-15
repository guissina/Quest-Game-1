package com.quest.services.ws;

import com.quest.config.websocket.WsDestinations;
import com.quest.dto.ws.Game.EngineStateDTO;
import com.quest.dto.ws.Game.TimerDTO;
import com.quest.engine.core.GameEngine;
import com.quest.engine.managers.GameSessionManager;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

@Service
public class TimerService {

    private static final int TICK_INTERVAL_MS = 1000; // 1s
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
        TURN (GameEngine::forceSkipTurn),
        QUESTION (GameEngine::forceFailQuestion);

        private final BiConsumer<GameEngine, Long> onTimeoutAction;

        TimerType(BiConsumer<GameEngine, Long> onTimeoutAction) {
            this.onTimeoutAction = onTimeoutAction;
        }
    }

    private record TimerKey(String sessionId, Long playerId, TimerType type) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TimerKey other)) return false;
            return sessionId.equals(other.sessionId)
                    && playerId.equals(other.playerId)
                    && type == other.type;
        }

        @Override
        public int hashCode() {
            return (sessionId + "#" + playerId + ";" + type).hashCode();
        }
    }

    private static class TimerHandles {
        ScheduledFuture<?> tickFuture;
        ScheduledFuture<?> timeoutFuture;
    }

    private void startTimer(String sessionId, Long playerId, int timeoutSeconds, TimerType type) {
        TimerType oppositeType = type == TimerType.TURN ? TimerType.QUESTION : TimerType.TURN;

        TimerKey key = new TimerKey(sessionId, playerId, type);
        TimerKey oppositeKey = new TimerKey(sessionId, playerId, oppositeType);

        cancelTimerInternal(oppositeKey);
        cancelTimerInternal(key);

        AtomicInteger secondsLeft = new AtomicInteger(timeoutSeconds);
        TimerHandles handles = new TimerHandles();
        timerTasks.put(key, handles);

        handles.tickFuture = scheduler.scheduleAtFixedRate(() -> {
            int seconds = secondsLeft.getAndDecrement();
            if (seconds >= 0) {
                String destination = String.format(WsDestinations.TIMER_TICK, sessionId);
                messagingTemplate.convertAndSend(destination, new TimerDTO(playerId, seconds, type.name()));
            }
            System.out.println("Timer: " + key + " - " + seconds + "s");
        }, 0, TICK_INTERVAL_MS, TimeUnit.MILLISECONDS);

        handles.timeoutFuture = scheduler.schedule(() -> {
            cancelTick(key);

            GameEngine engine = sessionManager.getEngine(sessionId);
            type.onTimeoutAction.accept(engine, playerId);

            String destination = String.format(WsDestinations.GAME_STATE, sessionId);
            messagingTemplate.convertAndSend(destination, EngineStateDTO.from(sessionId, engine));

            if (!engine.isFinished()) {
                Long nextPlayer = engine.getTurnManager().getCurrentPlayerId();
                startTimer(sessionId, nextPlayer, 15, TimerType.TURN);
            }
            System.out.println("Timer: " + key + " - Timeout");
        }, timeoutSeconds, TimeUnit.SECONDS);
    }

    private void cancelTimerInternal(TimerKey key) {
        TimerHandles handles = timerTasks.remove(key);
        if (handles != null) {
            if (handles.timeoutFuture != null && !handles.timeoutFuture.isDone())
                handles.timeoutFuture.cancel(false);

            if (handles.tickFuture != null && !handles.tickFuture.isDone())
                handles.tickFuture.cancel(false);
        }
    }

    private void cancelTick(TimerKey key) {
        TimerHandles handles = timerTasks.get(key);
        if (handles != null && handles.tickFuture != null && !handles.tickFuture.isDone())
            handles.tickFuture.cancel(false);
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

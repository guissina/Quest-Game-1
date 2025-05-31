package com.quest.services.ws;

import com.quest.dto.ws.Game.EngineStateDTO;
import com.quest.engine.core.GameEngine;
import com.quest.config.websocket.WsDestinations;
import com.quest.dto.ws.Game.TimerDTO;
import com.quest.engine.managers.GameSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuestionTimerService {

    private static final int TICK_INTERVAL_MS = 1000; // 1s
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Map<String, ScheduledFuture<?>> timeoutTasks = new ConcurrentHashMap<>();
    private final Map<String, ScheduledFuture<?>> tickTasks = new ConcurrentHashMap<>();

    private final GameSessionManager sessionManager;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public QuestionTimerService(GameSessionManager sessionManager,
                                SimpMessagingTemplate messagingTemplate) {
        this.sessionManager = sessionManager;
        this.messagingTemplate = messagingTemplate;
    }

    public void startQuestionTimer(String sessionId, Long playerId, int timeoutSeconds) {
        // Chave única por <sessionId>#<playerId>
        String key = sessionId + "#" + playerId;

        cancelQuestionTimer(sessionId, playerId);
        AtomicInteger secondsLeft = new AtomicInteger(timeoutSeconds);

        ScheduledFuture<?> tickFuture = scheduler.scheduleAtFixedRate(() -> {

            int seconds = secondsLeft.getAndDecrement();
            if (seconds >= 0) {
                String destination = String.format(WsDestinations.QUESTION_TIMER_TICK, sessionId);
                messagingTemplate.convertAndSend(destination, new TimerDTO(playerId, seconds));
            }
        }, 0, TICK_INTERVAL_MS, TimeUnit.MILLISECONDS);
        tickTasks.put(key, tickFuture);

        ScheduledFuture<?> timeoutFuture = scheduler.schedule(() -> {

            ScheduledFuture<?> tf = tickTasks.remove(key);
            if (tf != null && !tf.isDone())
                tf.cancel(false);

            GameEngine engine = sessionManager.getEngine(sessionId);
            engine.forceFailQuestion(playerId); // TODO Erro nao propagado na Thread do agendamento

            String destination = String.format(WsDestinations.GAME_STATE, sessionId);
            messagingTemplate.convertAndSend(destination, EngineStateDTO.from(sessionId, engine));
        }, timeoutSeconds, TimeUnit.SECONDS);

        timeoutTasks.put(key, timeoutFuture);
    }

    public void cancelQuestionTimer(String sessionId, Long playerId) {
        String key = sessionId + "#" + playerId;

        ScheduledFuture<?> timeoutFuture = timeoutTasks.remove(key);
        if (timeoutFuture != null && !timeoutFuture.isDone()) timeoutFuture.cancel(false);

        ScheduledFuture<?> tickFuture = tickTasks.remove(key);
        if (tickFuture != null && !tickFuture.isDone()) tickFuture.cancel(false);
    }
}

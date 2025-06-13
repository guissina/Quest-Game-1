package com.quest.engine.managers;

import com.quest.engine.core.GameEngine;
import com.quest.engine.core.GameRoom;
import com.quest.engine.core.GameSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameSessionManager {
    private final Map<String, GameSession> sessions = new ConcurrentHashMap<>();

    public String createSession(Boolean publicSession) {
        String id = UUID.randomUUID().toString();
        sessions.put(id, new GameSession(id, publicSession));
        return id;
    }

    public GameSession getSession(String sessionId) {
        GameSession session = sessions.get(sessionId);
        if (session == null)
            throw new IllegalArgumentException("Session not found");
        return session;
    }

    public GameEngine getEngine(String sessionId) {
        return getSession(sessionId).getEngine();
    }

    public GameRoom getRoom(String sessionId) {
        return getSession(sessionId).getRoom();
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    @Scheduled(fixedDelay = 600_000) // 10 min
    public void cleanupEmptySessions() {
        sessions.entrySet().removeIf(entry -> entry.getValue().getRoom().getPlayers().isEmpty());
    }
}

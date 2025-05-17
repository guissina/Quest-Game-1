package com.quest.engine.managers;

import com.quest.engine.core.GameEngine;
import com.quest.engine.core.GameSession;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameSessionManager {
    private final Map<String, GameSession> sessions = new ConcurrentHashMap<>();

    public String createSession() {
        String id = UUID.randomUUID().toString();
        sessions.put(id, new GameSession(id));
        return id;
    }

    public GameSession getSession(String sessionId) {
        GameSession session = sessions.get(sessionId);
        if (session == null) throw new IllegalArgumentException("Session not found");
        return session;
    }

    public GameEngine getEngine(String sessionId) {
        return getSession(sessionId).getEngine();
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}

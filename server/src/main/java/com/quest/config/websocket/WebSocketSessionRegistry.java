package com.quest.config.websocket;

import org.springframework.stereotype.Component;

import com.quest.engine.core.SessionInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionRegistry {

    // wsSessionId → (gameSessionId, playerId)
    private final Map<String, SessionInfo> sessions = new ConcurrentHashMap<>();

    public void register(String wsSessionId, String gameSessionId, Long playerId) {
        sessions.put(wsSessionId, new SessionInfo(gameSessionId, playerId));
    }

    public SessionInfo remove(String wsSessionId) {
        return sessions.remove(wsSessionId);
    }
}


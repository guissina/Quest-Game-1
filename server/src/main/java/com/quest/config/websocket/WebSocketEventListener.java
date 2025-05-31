package com.quest.config.websocket;

import com.quest.engine.core.SessionInfo;
import com.quest.interfaces.ws.IGameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private final WebSocketSessionRegistry registry;
    private final IGameRoomService roomService;

    @Autowired
    public WebSocketEventListener(WebSocketSessionRegistry registry,
                                  IGameRoomService roomService) {
        this.registry = registry;
        this.roomService = roomService;
    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        String wsSessionId = event.getSessionId();
        SessionInfo info = registry.remove(wsSessionId);

        if (info != null)
            roomService.removeAndBroadcast(info.gameSessionId(), info.playerId());
    }
}


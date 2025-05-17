package com.quest.config.websocket;

import com.quest.engine.core.GameRoom;
import com.quest.engine.managers.GameSessionManager;
import com.quest.interfaces.ws.IGameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {

    private final GameSessionManager sessionManager;
    private final IGameRoomService roomService;

    private final Map<String,String> wsSessionToGameSession = new ConcurrentHashMap<>();
    private final Map<String,Long> wsSessionToPlayerId = new ConcurrentHashMap<>();

    @Autowired
    public WebSocketEventListener(GameSessionManager sessionManager,
                                  IGameRoomService roomService,
                                  SimpMessagingTemplate messagingTemplate) {
        this.sessionManager = sessionManager;
        this.roomService = roomService;
    }

    // 1) Quando fizer SUBSCRIBE em /topic/game/{sessionId}/state
    @EventListener
    public void onSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String simpSessionId = headerAccessor.getSessionId();
        String destination = headerAccessor.getDestination(); // ex: /topic/game/abc-123/state

        // Verifica /topic/room/{sessionId}/players ou /topic/game/{sessionId}/state
        if (destination != null && destination.matches("/topic/(room|game)/[^/]+/.+")) {
            String gameSessionId = destination.split("/")[3];
            wsSessionToGameSession.put(simpSessionId, gameSessionId);

            List<String> header = headerAccessor.getNativeHeader("playerId");
            if (header != null && !header.isEmpty())
                wsSessionToPlayerId.put(simpSessionId, Long.valueOf(header.get(0)));
        }
    }

    // 2) Quando a conexão cai
    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        String simpSessionId = event.getSessionId();
        String gameSessionId = wsSessionToGameSession.remove(simpSessionId);
        Long playerId = wsSessionToPlayerId.remove(simpSessionId);

        if (gameSessionId != null && playerId != null) {
            GameRoom room = sessionManager.getRoom(gameSessionId);

            room.removePlayer(playerId);
            if (room.getPlayers().isEmpty())
                sessionManager.removeSession(gameSessionId);
            else
                roomService.removeAndBroadcast(gameSessionId, playerId);
        }
    }
}


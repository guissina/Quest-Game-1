package com.quest.controllers.ws;

import com.quest.config.websocket.WebSocketSessionRegistry;
import com.quest.dto.ws.Room.*;
import com.quest.interfaces.ws.IGameRoomService;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class GameRoomWsController {

    private final IGameRoomService service;
    private final WebSocketSessionRegistry registry;

    public GameRoomWsController(IGameRoomService service,
                                WebSocketSessionRegistry registry) {
        this.service = service;
        this.registry = registry;
    }

    @MessageMapping("/room/create")
    @SendToUser("/queue/room-created")
    public RoomCreateResponseDTO create(RoomCreateRequestDTO req) {
        return service.createRoom(req);
    }

    @MessageMapping("/room/join")
    public void join(@Header("simpSessionId") String wsSessionId,
                     JoinRoomRequestDTO req) {
        service.joinRoom(req);
        registry.register(wsSessionId, req.sessionId(), req.playerId());
    }

    @MessageMapping("/room/start")
    public void start(StartRoomRequestDTO req) {
        service.startRoom(req);
    }

    @MessageMapping("/room/leave")
    public void leave(LeaveRoomRequestDTO req) {
        service.leaveRoom(req);
    }
}

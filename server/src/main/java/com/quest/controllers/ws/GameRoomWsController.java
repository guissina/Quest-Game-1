package com.quest.controllers.ws;

import com.quest.dto.ws.Room.JoinRoomRequestDTO;
import com.quest.dto.ws.Room.RoomCreateRequestDTO;
import com.quest.dto.ws.Room.RoomCreateResponseDTO;
import com.quest.dto.ws.Room.StartRoomRequestDTO;
import com.quest.services.ws.GameRoomService;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class GameRoomWsController {

    private final GameRoomService service;

    public GameRoomWsController(GameRoomService service) {
        this.service = service;
    }

    @MessageMapping("/room/create")
    @SendToUser("/queue/room-created")
    public RoomCreateResponseDTO create(RoomCreateRequestDTO req) {
        return service.createRoom(req);
    }

    @MessageMapping("/room/join")
    public void join(JoinRoomRequestDTO req) {
        service.joinRoom(req);
    }

    @MessageMapping("/room/start")
    public void start(StartRoomRequestDTO req) {
        service.startRoom(req);
    }

    // TODO Implementar leave utilizando RemovePlayerRequestDTO
}

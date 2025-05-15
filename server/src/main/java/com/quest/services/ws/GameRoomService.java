package com.quest.services.ws;

import com.quest.config.websocket.WsDestinations;
import com.quest.dto.ws.Room.PlayerRoomResponseDTO;
import com.quest.dto.ws.Room.JoinRoomRequestDTO;
import com.quest.dto.ws.Room.JoinRoomResponseDTO;
import com.quest.dto.ws.Room.RoomCreateRequestDTO;
import com.quest.dto.ws.Room.RoomCreateResponseDTO;
import com.quest.engine.core.GameEngine;
import com.quest.engine.core.GameRoom;
import com.quest.engine.managers.GameRoomManager;
import com.quest.interfaces.IPlayerServices;
import com.quest.models.Board;
import com.quest.models.Player;
import com.quest.mappers.PlayerMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameRoomService {

    private final GameRoomManager roomManager;
    private final SimpMessagingTemplate messagingTemplate;
    private final IPlayerServices playerServices;
    private final PlayerMapper playerMapper;

    @Autowired
    public GameRoomService(GameRoomManager roomManager,
                           SimpMessagingTemplate messagingTemplate,
                           IPlayerServices playerServices,
                           PlayerMapper playerMapper) {
        this.roomManager = roomManager;
        this.messagingTemplate = messagingTemplate;
        this.playerServices = playerServices;
        this.playerMapper = playerMapper;
    }

    public RoomCreateResponseDTO createRoom(RoomCreateRequestDTO req) {
        String id = UUID.randomUUID().toString();
        roomManager.createRoom(id);
        return new RoomCreateResponseDTO(id);
    }

    public void joinRoom(JoinRoomRequestDTO req) {
        GameRoom room = roomManager.getRoom(req.getRoomId());

        // TODO Verificar se o player ja esta na sala (Service ou Manager?)
        Player player = playerServices.findPlayerById(req.getPlayerId());
        boolean ok = room.addPlayer(player);
        if (!ok) throw new IllegalStateException("Room full or already started");

        List<PlayerRoomResponseDTO> playersDTO = playerMapper.toPlayerRoomResponseDTOs(room.getPlayers());

        JoinRoomResponseDTO joinResponse = new JoinRoomResponseDTO(playersDTO, room.isStarted());
        messagingTemplate.convertAndSend(
                String.format(WsDestinations.ROOM_PLAYERS, req.getRoomId()),
                joinResponse
        );
    }

    // TODO Isso deve receber boardId
    public void startRoom(String roomId, Board board) {
        GameRoom room = roomManager.getRoom(roomId);
        GameEngine engine = new GameEngine(room, board);
        room.startGame(engine);

        messagingTemplate.convertAndSend(
                String.format(WsDestinations.ROOM_START, roomId),
                engine.getState()
        );
    }
}

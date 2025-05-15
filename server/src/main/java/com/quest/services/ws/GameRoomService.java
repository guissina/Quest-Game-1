package com.quest.services.ws;

import com.quest.config.websocket.WsDestinations;
import com.quest.dto.ws.Game.EngineStateDTO;
import com.quest.dto.ws.Room.*;
import com.quest.engine.core.GameEngine;
import com.quest.engine.core.GameRoom;
import com.quest.engine.core.GameSession;
import com.quest.engine.managers.GameSessionManager;
import com.quest.interfaces.rest.IPlayerServices;
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

    private final GameSessionManager sessionManager;
    private final SimpMessagingTemplate messagingTemplate;
    private final IPlayerServices playerServices;
    private final PlayerMapper playerMapper;
    //private final BoardService boardService;

    @Autowired
    public GameRoomService(
            GameSessionManager sessionManager,
            SimpMessagingTemplate messagingTemplate,
            IPlayerServices playerServices,
            PlayerMapper playerMapper
    ) {
        this.sessionManager = sessionManager;
        this.messagingTemplate = messagingTemplate;
        this.playerServices = playerServices;
        this.playerMapper = playerMapper;
    }

    public RoomCreateResponseDTO createRoom(RoomCreateRequestDTO req) {
        String sessionId = sessionManager.createSession();
        return new RoomCreateResponseDTO(sessionId);
    }

    public void joinRoom(JoinRoomRequestDTO req) {
        GameSession session = sessionManager.getSession(req.sessionId());
        GameRoom room = session.getRoom();

        // TODO Verificar se o player ja esta na sala (Service ou Manager?)
        Player player = playerServices.findPlayerById(req.playerId());
        boolean ok = room.addPlayer(player);
        if (!ok)
            throw new IllegalStateException("Room full or already started");

        List<PlayerRoomResponseDTO> playersDTO =
                playerMapper.toPlayerRoomResponseDTOs(room.getPlayers());

        JoinRoomResponseDTO joinResponse =
                new JoinRoomResponseDTO(playersDTO, room.isStarted());

        String destination = String.format(WsDestinations.ROOM_PLAYERS, req.sessionId());
        messagingTemplate.convertAndSend(destination, joinResponse);
    }


    public void startRoom(StartRoomRequestDTO req) {
        GameSession session = sessionManager.getSession(req.sessionId());
        GameRoom room = session.getRoom();

        Board board = /*TODO boardService.findById(req.boardId())*/ null;
        GameEngine engine = new GameEngine(room, board);
        engine.initializeGameState(req.initialTokens());
        engine.seed();
        session.startGame(engine);

        EngineStateDTO stateDto = EngineStateDTO.from(session.getSessionId(), engine);

        String destination = String.format(WsDestinations.ROOM_START, session.getSessionId());
        messagingTemplate.convertAndSend(destination, stateDto);
    }
}

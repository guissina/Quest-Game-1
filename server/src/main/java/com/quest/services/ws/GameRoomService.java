package com.quest.services.ws;

import com.quest.config.websocket.WsDestinations;
import com.quest.dto.ws.Room.*;
import com.quest.engine.core.GameEngine;
import com.quest.engine.core.GameRoom;
import com.quest.engine.core.GameSession;
import com.quest.engine.managers.GameSessionManager;
import com.quest.interfaces.rest.IBoardServices;
import com.quest.interfaces.rest.IPlayerServices;
import com.quest.interfaces.ws.IGameRoomService;
import com.quest.models.Board;
import com.quest.models.Player;
import com.quest.mappers.PlayerMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRoomService implements IGameRoomService {

    private final GameSessionManager sessionManager;
    private final SimpMessagingTemplate messagingTemplate;
    private final IBoardServices boardService;
    private final IPlayerServices playerServices;
    private final PlayerMapper playerMapper;

    @Autowired
    public GameRoomService(
            GameSessionManager sessionManager,
            SimpMessagingTemplate messagingTemplate,
            IBoardServices boardService,
            IPlayerServices playerServices,
            PlayerMapper playerMapper
    ) {
        this.sessionManager = sessionManager;
        this.messagingTemplate = messagingTemplate;
        this.boardService = boardService;
        this.playerServices = playerServices;
        this.playerMapper = playerMapper;
    }

    private void broadcastRoomState(String sessionId, boolean closed) {
        GameSession session = sessionManager.getSession(sessionId);
        GameRoom room = session.getRoom();

        List<PlayerRoomResponseDTO> playersDTO =
                playerMapper.toPlayerRoomResponseDTOs(room.getPlayers());

        RoomStateDTO dto = new RoomStateDTO(sessionId, playersDTO, room.isStarted(), closed);
        String destination = String.format(WsDestinations.ROOM_STATE, sessionId);
        messagingTemplate.convertAndSend(destination, dto);
    }

    @Override
    public RoomCreateResponseDTO createRoom(RoomCreateRequestDTO req) {
        String sessionId = sessionManager.createSession();
        return new RoomCreateResponseDTO(sessionId);
    }

    @Override
    public void joinRoom(JoinRoomRequestDTO req) {
        GameRoom room = sessionManager.getRoom(req.sessionId());

        // TODO Verificar se o player ja esta na sala (Service ou Manager?)
        Player player = playerServices.findPlayerById(req.playerId());
        boolean ok = room.addPlayer(player);
        if (!ok)
            throw new IllegalStateException("Room full or already started");

        broadcastRoomState(req.sessionId(), false);
    }

    @Override
    public void startRoom(StartRoomRequestDTO req) {
        GameSession session = sessionManager.getSession(req.sessionId());
        GameRoom room = session.getRoom();

        // TODO só o creator (ou host) deve conseguir iniciar a sala

        Board board = boardService.findBoardById(req.boardId());
        GameEngine engine = new GameEngine(room, board);
        engine.initializeGameState(req.initialTokens());
        engine.seed();
        session.startGame(engine);

        broadcastRoomState(req.sessionId(), false);
    }

    @Override
    public void leaveRoom(LeaveRoomRequestDTO req) {
        removeAndBroadcast(req.sessionId(), req.playerId());
    }

    @Override
    public void removeAndBroadcast(String sessionId, Long playerId) {
        GameRoom room = sessionManager.getRoom(sessionId);

        room.removePlayer(playerId);
        if (room.getPlayers().isEmpty()) {
            broadcastRoomState(sessionId, true);
            sessionManager.removeSession(sessionId);
        }
        else
            broadcastRoomState(sessionId, false);
    }
}

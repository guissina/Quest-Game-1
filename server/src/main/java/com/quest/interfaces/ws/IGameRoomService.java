package com.quest.interfaces.ws;

import com.quest.dto.ws.Room.*;

import java.util.List;

public interface IGameRoomService {
    void broadcastRoomState(String sessionId, boolean closed);

    RoomCreateResponseDTO createRoom(RoomCreateRequestDTO req);

    void joinRoom(JoinRoomRequestDTO req);

    List<RoomSummaryDTO> listPublicRooms();

    void startRoom(StartRoomRequestDTO req);

    void closeRoom(String sessionId);

    void leaveRoom(LeaveRoomRequestDTO req);

    void changeVisibility(ChangeVisibilityRoomRequestDTO req);

    void removeAndBroadcast(String roomId, Long playerId);
}

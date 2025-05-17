package com.quest.interfaces.ws;

import com.quest.dto.ws.Room.*;

public interface IGameRoomService {
    RoomCreateResponseDTO createRoom(RoomCreateRequestDTO req);

    void joinRoom(JoinRoomRequestDTO req);

    void startRoom(StartRoomRequestDTO req);

    void leaveRoom(LeaveRoomRequestDTO req);

    void removeAndBroadcast(String roomId, Long playerId);
}

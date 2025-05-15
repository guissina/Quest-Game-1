package com.quest.interfaces.ws;

import com.quest.dto.ws.Room.JoinRoomRequestDTO;
import com.quest.dto.ws.Room.RoomCreateRequestDTO;
import com.quest.dto.ws.Room.RoomCreateResponseDTO;
import com.quest.models.Board;

public interface IGameRoomService {
    RoomCreateResponseDTO createRoom(RoomCreateRequestDTO req);

    void joinRoom(JoinRoomRequestDTO req);

    void startRoom(String roomId, Board board);

}

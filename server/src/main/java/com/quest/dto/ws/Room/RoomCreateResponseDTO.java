package com.quest.dto.ws.Room;

public class RoomCreateResponseDTO {

    private String roomId;

    public RoomCreateResponseDTO() { }

    public RoomCreateResponseDTO(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
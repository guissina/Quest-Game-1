package com.quest.dto.ws.Room;

public class JoinRoomRequestDTO {
    
    private String roomId;
    private Long playerId;

    public JoinRoomRequestDTO() { }

    public JoinRoomRequestDTO(String roomId, Long playerId) {
        this.roomId = roomId;
        this.playerId = playerId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}

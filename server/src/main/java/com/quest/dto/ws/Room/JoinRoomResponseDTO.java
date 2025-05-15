package com.quest.dto.ws.Room;

import java.util.List;

public class JoinRoomResponseDTO {

    private List<PlayerRoomResponseDTO> players;
    private boolean started;

    public JoinRoomResponseDTO() { }

    public JoinRoomResponseDTO(List<PlayerRoomResponseDTO> players, boolean started) {
        this.players = players;
        this.started = started;
    }

    public List<PlayerRoomResponseDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerRoomResponseDTO> players) {
        this.players = players;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
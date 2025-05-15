package com.quest.dto.ws.Room;

import java.util.List;

public record JoinRoomResponseDTO(
        List<PlayerRoomResponseDTO> players,
        boolean started
) { }
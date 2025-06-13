package com.quest.dto.ws.Room;

import java.util.List;

public record RoomStateDTO (
        String sessionId,
        List<PlayerRoomResponseDTO> players,
        boolean started,
        boolean closed,
        Long hostId
) { }

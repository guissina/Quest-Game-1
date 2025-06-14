package com.quest.dto.ws.Room;

import java.util.List;

public record RoomStateDTO (
        String sessionId,
        List<PlayerRoomResponseDTO> players,
        boolean started,
        boolean closed,
        Boolean publicSession,
        Long hostId
) { }

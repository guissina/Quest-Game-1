package com.quest.dto.ws.Room;

public record RoomSummaryDTO(
        String sessionId,
        Long hostId,
        int playerCount,
        boolean started
) {}

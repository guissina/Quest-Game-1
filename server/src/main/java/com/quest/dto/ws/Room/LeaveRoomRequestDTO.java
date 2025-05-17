package com.quest.dto.ws.Room;

public record LeaveRoomRequestDTO(
        String sessionId,
        Long playerId
) {}

package com.quest.dto.ws.Room;

public record JoinRoomRequestDTO(
        String sessionId,
        Long playerId
) { }

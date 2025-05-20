package com.quest.dto.ws.Room;

public record SessionInfo(
        String gameSessionId,
        Long   playerId
) {}
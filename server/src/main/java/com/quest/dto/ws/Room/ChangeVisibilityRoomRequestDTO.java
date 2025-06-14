package com.quest.dto.ws.Room;

public record ChangeVisibilityRoomRequestDTO (
        String sessionId,
        Boolean publicSession
) { }

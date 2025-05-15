package com.quest.dto.ws.Room;

public record StartRoomRequestDTO(
        String sessionId,
        Long boardId,
        int initialTokens
) { }

package com.quest.dto.ws.Room;

import java.util.List;

public record StartRoomRequestDTO(
        String sessionId,
        Long boardId,
        List<Long> themeIds,
        int initialTokens
) { }

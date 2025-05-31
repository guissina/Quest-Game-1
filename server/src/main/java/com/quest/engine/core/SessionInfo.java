package com.quest.engine.core;

public record SessionInfo(
        String gameSessionId,
        Long   playerId
) {}
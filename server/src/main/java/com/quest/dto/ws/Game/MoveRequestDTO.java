package com.quest.dto.ws.Game;

public record MoveRequestDTO(
        Long playerId,
        int steps
) { }

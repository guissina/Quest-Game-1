package com.quest.dto.ws.Game;

public record AnswerRequestDTO(
        Long playerId,
        Long selectedOptionId,
        int steps
) { }

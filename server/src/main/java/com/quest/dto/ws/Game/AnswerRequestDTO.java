package com.quest.dto.ws.Game;

public record AnswerRequestDTO(
        Long playerId,
        Long questionId,
        Long selectedOptionId,
        int steps
) { }

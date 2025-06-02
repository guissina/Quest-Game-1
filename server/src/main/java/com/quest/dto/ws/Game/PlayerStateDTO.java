package com.quest.dto.ws.Game;

import java.util.List;

import com.quest.dto.rest.Question.QuestionResponseDTO;
import com.quest.engine.state.PlayerState;

public record PlayerStateDTO(
        Long playerId,
        Long tileId,
        List<Integer> tokens,
        boolean isCurrentTurn,
        QuestionResponseDTO pendingQuestion,
        Integer pendingSteps,
        Integer correctCount) {

    public static PlayerStateDTO from(PlayerState state, boolean isCurrentTurn) {
        return new PlayerStateDTO(
                state.getPlayerId(),
                state.getCurrentTileId(),
                state.getTokens(),
                isCurrentTurn,
                state.getPendingQuestion() != null ? QuestionResponseDTO.from(state.getPendingQuestion()) : null,
                state.getPendingSteps() != null ? state.getPendingSteps() : null,
                state.getCorrectCount() != null ? state.getCorrectCount() : null
                );
    }
}

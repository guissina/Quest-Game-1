package com.quest.dto.ws.Game;

import com.quest.dto.rest.Question.QuestionResponseDTO;
import com.quest.engine.state.PlayerState;

import java.util.List;

public record PlayerStateDTO(
        Long playerId,
        Long tileId,
        List<Integer> tokens,
        boolean isCurrentTurn,
        QuestionResponseDTO pendingQuestion
) {

    public static PlayerStateDTO from(PlayerState state, boolean isCurrentTurn) {
        return new PlayerStateDTO(
                state.getPlayerId(),
                state.getCurrentTileId(),
                state.getTokens(),
                isCurrentTurn,
                state.getPendingQuestion() != null ? QuestionResponseDTO.from(state.getPendingQuestion()) : null
        );
    }
}

package com.quest.dto.ws.Game;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.quest.dto.rest.Question.QuestionResponseDTO;
import com.quest.engine.state.PlayerState;

public record PlayerStateDTO(
        Long playerId,
        Long tileId,
        List<Integer> tokens,
        boolean isCurrentTurn,
        QuestionResponseDTO pendingQuestion,
        Integer pendingSteps,
        Integer correctCount,
        Map<String, Boolean> abilities) {

    public static PlayerStateDTO from(PlayerState state, boolean isCurrentTurn) {
        Map<String, Boolean> abilitiesMap = state.getAbilitiesMap().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> entry.getValue().isActive()));

        return new PlayerStateDTO(
                state.getPlayerId(),
                state.getCurrentTileId(),
                state.getTokens(),
                isCurrentTurn,
                state.getPendingQuestion() != null ? QuestionResponseDTO.from(state.getPendingQuestion()) : null,
                state.getPendingSteps() != null ? state.getPendingSteps() : null,
                state.getCorrectCount() != null ? state.getCorrectCount() : null,
                abilitiesMap);

    }
}

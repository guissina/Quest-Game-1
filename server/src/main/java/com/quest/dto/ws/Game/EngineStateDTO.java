package com.quest.dto.ws.Game;

import com.quest.engine.core.GameEngine;
import com.quest.engine.state.BoardState;

import java.util.List;

public record EngineStateDTO(
        String sessionId,
        BoardState board,
        List<PlayerStateDTO> playerStates,
        Long winnerId,
        boolean finished
) {
    public static EngineStateDTO from(String sessionId, GameEngine engine) {
        BoardState boardState = engine.getBoardManager().getBoardState();

        Long currentPlayerId = engine.getTurnManager().getCurrentPlayerId();
        List<PlayerStateDTO> playerStates = engine.getAllPlayerStates().values().stream()
                .map(ps -> {
                    boolean isCurrent = ps.getPlayerId().equals(currentPlayerId);
                    return PlayerStateDTO.from(ps, isCurrent);
                })
                .toList();

        Long winnerId = engine.getWinnerId().orElse(null);
        boolean finished = engine.isFinished();

        return new EngineStateDTO(sessionId, boardState, playerStates, winnerId, finished);
    }
}

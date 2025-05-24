package com.quest.dto.ws.Game;

import com.quest.engine.core.GameEngine;
import com.quest.engine.state.PlayerState;
import com.quest.models.Board;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public record EngineStateDTO(
        String sessionId,
        Board board,
        List<PlayerStateDTO> playerStates,
        Long winnerId,
        boolean finished
) {
    public static EngineStateDTO from(String sessionId, GameEngine engine) {
        Board board = engine.getBoardManager().getBoard();
        Collection<PlayerState> players = engine.getAllPlayerStates().values();
        Long currentPlayerId = engine.getTurnManager().getCurrentPlayerId();

        List<PlayerStateDTO> states = players.stream()
                .map(ps -> {
                    boolean isCurrent = ps.getPlayerId().equals(currentPlayerId);
                    return PlayerStateDTO.from(ps, isCurrent);
                })
                .collect(Collectors.toList());

        Long winnerId = engine.getWinnerId().orElse(null);
        return new EngineStateDTO(sessionId, board, states, winnerId, engine.isFinished());
    }
}

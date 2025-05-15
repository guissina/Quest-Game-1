package com.quest.dto.ws.Game;

import com.quest.engine.core.GameEngine;
import com.quest.engine.state.PlayerState;
import com.quest.models.Board;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record EngineStateDTO(
        String sessionId,
        Board board,
        List<PlayerStateDTO> playerStates
) {
    public static EngineStateDTO from(String sessionId, GameEngine engine) {
        Board board = engine.getBoardManager().getBoard();
        Map<Long, PlayerState> stateMap = engine.getStateByPlayer();
        Long currentPlayerId = engine.getTurnManager().getCurrentPlayerId();

        List<PlayerStateDTO> states = engine.getRoom().getPlayers().stream()
                .map(player -> {
                    PlayerState ps = stateMap.get(player.getId());
                    boolean isCurrent = player.getId().equals(currentPlayerId);
                    return PlayerStateDTO.from(player, ps, isCurrent);
                })
                .collect(Collectors.toList());

        return new EngineStateDTO(sessionId, board, states);
    }
}

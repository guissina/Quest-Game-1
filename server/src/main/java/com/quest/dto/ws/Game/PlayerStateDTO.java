package com.quest.dto.ws.Game;

import com.quest.engine.state.PlayerState;
import com.quest.models.Player;

public record PlayerStateDTO(
        Long playerId,
        Long tileId,
        int tokens,
        boolean mustAnswerBeforeMoving,
        boolean isCurrentTurn
) {

    public static PlayerStateDTO from(Player player, PlayerState state, boolean isCurrentTurn) {
        return new PlayerStateDTO(
                player.getId(),
                state.getCurrentTileId(),
                state.getTokens(),
                state.isMustAnswerBeforeMoving(),
                isCurrentTurn
        );
    }
}

package com.quest.engine.managers;

import com.quest.engine.state.PlayerState;

import java.util.Map;

public class QuestionManager {
    private final Map<Long, PlayerState> stateByPlayer;

    public QuestionManager(Map<Long, PlayerState> stateByPlayer) {
        this.stateByPlayer = stateByPlayer;
    }

    public void verifyCanMove(Long playerId) {
        PlayerState ps = stateByPlayer.get(playerId);
        if (ps == null)
            throw new RuntimeException("Player " + playerId + " not found.");
        if (ps.isMustAnswerBeforeMoving())
            throw new RuntimeException("Player " + playerId + " must answer before moving.");
    }

    public void process(Long playerId, int steps, boolean correct) {
        PlayerState ps = stateByPlayer.get(playerId);
        if (ps == null)
            throw new RuntimeException("Player " + playerId + " not found.");

        if (correct)
            ps.setMustAnswerBeforeMoving(false);
        else {
            if (!ps.isMustAnswerBeforeMoving())
                ps.consumeTokens(steps);
            ps.setMustAnswerBeforeMoving(true);
        }
    }
}

package com.quest.engine.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.quest.models.Question;

public class PlayerState {

    private final Long playerId;
    private final List<Integer> tokens;
    private Long currentTileId;
    private Question pendingQuestion;
    private int pendingSteps;

    public int getPendingSteps() {
        return pendingSteps;
    }

    public void setPendingSteps(int pendingSteps) {
        this.pendingSteps = pendingSteps;
    }

    public PlayerState(Long playerId, List<Integer> initialTokens, Long startTileId) {
        this.playerId = playerId;
        this.tokens = new ArrayList<>(initialTokens);
        this.currentTileId = startTileId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public List<Integer> getTokens() {
        return Collections.unmodifiableList(tokens);
    }

    public void consumeTokens(int steps) {
        if (!tokens.contains(steps))
            throw new IllegalStateException("Player does not have enough tokens");
        tokens.remove((Integer) steps);
    }

    public void resetTokens(List<Integer> initialTokens) {
        tokens.clear();
        tokens.addAll(initialTokens);
    }

    public Long getCurrentTileId() {
        return currentTileId;
    }

    public void moveTo(Long tileId) {
        this.currentTileId = tileId;
    }

    public Question getPendingQuestion() {
        return pendingQuestion;
    }

    public void setPendingQuestion(Question q) {
        this.pendingQuestion = q;
    }

    public void clearPendingQuestion() {
        this.pendingQuestion = null;
    }
}

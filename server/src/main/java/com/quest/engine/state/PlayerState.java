package com.quest.engine.state;

import com.quest.models.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerState {

    private final Long playerId;
    private final List<Integer> tokens;
    private Long currentTileId;
    private Question pendingQuestion;

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

    public void addToken(int value) {
        tokens.add(value);
    }

    public void consumeToken(int value) {
        tokens.remove((Integer) value);
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

    public Long getCurrentTileId() {
        return currentTileId;
    }

    public void moveTo(Long tileId) {
        this.currentTileId = tileId;
    }
}

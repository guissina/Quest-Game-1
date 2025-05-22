package com.quest.engine.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerState {
    private final Long playerId;
    private final List<Integer> tokens;
    private Long currentTileId;
    private boolean mustAnswerBeforeMoving;

    public PlayerState(Long playerId, List<Integer> initialTokens, Long startTileId) {
        this.playerId = playerId;
        this.tokens = new ArrayList<>(initialTokens);
        this.currentTileId = startTileId;
        this.mustAnswerBeforeMoving = false;
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

    public Long getCurrentTileId() {
        return currentTileId;
    }

    public void moveTo(Long tileId) {
        this.currentTileId = tileId;
    }

    public boolean isMustAnswerBeforeMoving() {
        return mustAnswerBeforeMoving;
    }

    public void setMustAnswerBeforeMoving(boolean must) {
        this.mustAnswerBeforeMoving = must;
    }
}

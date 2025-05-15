package com.quest.engine.state;

public class PlayerState {
    private final Long playerId;
    private int tokens;
    private Long currentTileId;
    private boolean mustAnswerBeforeMoving;

    public PlayerState(Long playerId, int initialTokens, Long startTileId) {
        this.playerId = playerId;
        this.tokens = initialTokens;
        this.currentTileId = startTileId;
        this.mustAnswerBeforeMoving = false;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public int getTokens() {
        return tokens;
    }

    public void consumeTokens(int amount) {
        this.tokens = Math.max(0, tokens - amount);
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

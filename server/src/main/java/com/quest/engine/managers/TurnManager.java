package com.quest.engine.managers;

import com.quest.engine.state.PlayerState;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class TurnManager {
    private final Deque<PlayerState> turnQueue = new ArrayDeque<>();

    public TurnManager(Collection<PlayerState> initialStates) {
        turnQueue.addAll(initialStates);
    }

    public void enqueuePlayer(PlayerState ps) {
        turnQueue.offerLast(ps);
    }

    public void dequeuePlayer(Long playerId) {
        turnQueue.removeIf(ps -> ps.getPlayerId().equals(playerId));
    }

    public void verifyTurn(Long playerId) {
        if (turnQueue.isEmpty())
            throw new IllegalStateException("No players in turn queue.");
        if (!turnQueue.peekFirst().getPlayerId().equals(playerId))
            throw new IllegalStateException("Not your turn.");
    }

    public void nextTurn() {
        if (turnQueue.isEmpty()) return;
        PlayerState head = turnQueue.pollFirst();
        turnQueue.offerLast(head);
    }

    public Long getCurrentPlayerId() {
        if (turnQueue.isEmpty())
            throw new IllegalStateException("No players in turn queue.");
        return turnQueue.peekFirst().getPlayerId();
    }
}


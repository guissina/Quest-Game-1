package com.quest.engine.managers;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import com.quest.engine.state.PlayerState;

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
        if (turnQueue.isEmpty())
            return;
        PlayerState head = turnQueue.pollFirst();
        turnQueue.offerLast(head);
    }

    public void skipNextTurn() {
        Long currentPlayer = getCurrentPlayerId();
        System.out.println("SKIP: Current player before: " + currentPlayer);

        nextTurn(); // Vai para o oponente
        Long nextPlayer = getCurrentPlayerId();
        System.out.println("SKIP: Next player (being skipped): " + nextPlayer);

        nextTurn(); // Pula o oponente e volta
        Long finalPlayer = getCurrentPlayerId();
        System.out.println("SKIP: Final player after skip: " + finalPlayer);

    }

    public Long getCurrentPlayerId() {
        if (turnQueue.isEmpty())
            throw new IllegalStateException("No players in turn queue.");
        return turnQueue.peekFirst().getPlayerId();
    }
}

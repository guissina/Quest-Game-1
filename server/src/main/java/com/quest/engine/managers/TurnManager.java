package com.quest.engine.managers;

import com.quest.engine.state.PlayerState;

import java.util.List;

public class TurnManager {
    private final List<PlayerState> players;
    private int currentTurn = 0; // TODO Se o jogador sair no meio do turno

    // TODO Essa lista pode quebrar caso remova ou adicione um player novo, pois e um clone e nao referencia
    public TurnManager(List<PlayerState> players) {
        this.players = players;
    }

    public void verifyTurn(Long playerId) {
        if (players.isEmpty())
            throw new RuntimeException("No players in room.");

        PlayerState current = players.get(currentTurn);
        if (!current.getPlayerId().equals(playerId))
            throw new RuntimeException("Not your turn.");
    }

    public void nextTurn() {
        if (players.isEmpty()) return;
        currentTurn = (currentTurn + 1) % players.size();
    }

    public int getCurrentTurn() { return currentTurn; }

    public Long getCurrentPlayerId() {
        return players.get(currentTurn).getPlayerId();
    }
}


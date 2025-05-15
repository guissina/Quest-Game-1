package com.quest.engine.managers;

import com.quest.engine.core.GameRoom;
import com.quest.models.Player;

import java.util.List;

public class TurnManager {

    private final GameRoom room;
    private int currentTurn = 0; // TODO Se o jogador sair no meio do turno

    public TurnManager(GameRoom room) {
        this.room = room;
    }

    public void verifyTurn(Long playerId) {
        List<Player> players = room.getPlayers();
        if (players.isEmpty())
            throw new RuntimeException("No players in room.");

        Player current = players.get(currentTurn);
        if (!current.getId().equals(playerId))
            throw new RuntimeException("Not your turn.");
    }

    public void nextTurn() {
        List<Player> players = room.getPlayers();
        if (players.isEmpty()) return;
        currentTurn = (currentTurn + 1) % players.size();
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public Player getCurrentPlayer() {
        List<Player> players = room.getPlayers();
        if (players.isEmpty())
            throw new RuntimeException("No players in room.");
        return players.get(currentTurn);
    }

    // TODO Lancar excessoes personalizadas
}

package com.quest.engine.core;

import com.quest.models.Player;
import com.quest.dto.ws.Game.EngineStateDTO;
import com.quest.models.Board;

import java.util.List;

public class GameEngine {
    
    private final String gameId;
    private Board board;
    private List<Player> players;
    private int currentTurn = 0;

    public GameEngine(String gameId, Board board, List<Player> players) {
        this.gameId = gameId;
        this.board  = board;
        this.players = players;
    }

    public void seed() {
        // TODO: mover players para tile inicial
    }

    public EngineStateDTO getState() {
        return new EngineStateDTO(gameId, board, players, currentTurn);
    }

    // TODO: futuras ações: move(playerId, tileId), answer(...), playCard(...)
}

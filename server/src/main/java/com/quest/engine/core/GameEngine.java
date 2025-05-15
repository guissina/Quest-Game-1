package com.quest.engine.core;

import com.quest.engine.managers.BoardManager;
import com.quest.engine.managers.TurnManager;
import com.quest.models.Player;
import com.quest.dto.ws.Game.EngineStateDTO;
import com.quest.models.Board;
import com.quest.models.Tile;

public class GameEngine {

    private final GameRoom room;
    private final TurnManager turnManager;
    private final BoardManager boardManager;

    public GameEngine(GameRoom room, Board board) {
        this.room = room;
        this.turnManager = new TurnManager(room);
        this.boardManager = new BoardManager(board, room);
    }

    public void seed() {
        boardManager.seed();
    }

    public void move(Long playerId, Long tileId) {
        turnManager.verifyTurn(playerId);

        Player player = room.findPlayerById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found."));
        Tile tile = boardManager.getBoard().findTileById(tileId)
                .orElseThrow(() -> new RuntimeException("Tile not found."));

        boardManager.move(player, tile);
        turnManager.nextTurn();
    }

    public EngineStateDTO getState() {
        return new EngineStateDTO(
                room.getRoomId(),
                boardManager.getBoard(),
                room.getPlayers(),
                turnManager.getCurrentTurn()
        );
    }

    public Player getCurrentPlayer() {
        return turnManager.getCurrentPlayer();
    }
}

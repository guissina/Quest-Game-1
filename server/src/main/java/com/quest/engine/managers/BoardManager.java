package com.quest.engine.managers;

import com.quest.engine.core.GameRoom;
import com.quest.models.Board;
import com.quest.models.Player;
import com.quest.models.Tile;

public class BoardManager {

    private final Board board;
    private final GameRoom room;

    public BoardManager(Board board, GameRoom room) {
        this.board = board;
        this.room = room;
    }

    public Board getBoard() {
        return board;
    }

    public void seed() {
        Tile start = board.initialTile();
        if (start == null)
            throw new RuntimeException("No start tile.");

        board.getTiles().forEach(t -> t.getPlayers().clear());
        room.getPlayers().forEach(p -> start.getPlayers().add(p));
    }

    public void move(Player player, Tile destination) {
        for (Tile t : board.getTiles()) {
            t.getPlayers().removeIf(p -> p.getId().equals(player.getId()));
        }
        destination.getPlayers().add(player);
    }
}

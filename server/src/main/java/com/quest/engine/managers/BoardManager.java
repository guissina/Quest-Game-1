package com.quest.engine.managers;

import com.quest.engine.state.PlayerState;
import com.quest.models.Board;
import com.quest.models.Tile;

import java.util.Map;

public class BoardManager {

    private final Board board;

    public BoardManager(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public void seed(Map<Long, PlayerState> stateByPlayer) {
        Long startId = board.getStartTile().getId();
        stateByPlayer.values()
                .forEach(ps -> ps.moveTo(startId));
    }

    public void movePlayer(PlayerState ps, Tile destination) {
        ps.moveTo(destination.getId());
    }
}

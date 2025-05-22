package com.quest.engine.managers;

import com.quest.engine.state.PlayerState;
import com.quest.models.Board;
import com.quest.models.Tile;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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

    public Tile getTileAtOffset(Long currentTileId, int steps) {
        List<Tile> tiles = board.getTiles();
        int size = tiles.size();

        int currentIndex = IntStream.range(0, size)
                .filter(i -> tiles.get(i).getId().equals(currentTileId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tile atual não encontrado"));

        int destinationIndex = (currentIndex + steps) % size;
        return tiles.get(destinationIndex);
    }
}

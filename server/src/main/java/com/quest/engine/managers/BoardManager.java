package com.quest.engine.managers;

import com.quest.engine.state.PlayerState;
import com.quest.models.Board;
import com.quest.models.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardManager {

    private final Board board;
    private final Map<Long,Integer> indexById;

    public BoardManager(Board board) {
        this.board = board;
        this.indexById = new HashMap<>(board.getTiles().size());

        List<Tile> tiles = board.getTiles();
        for (int i = 0; i < tiles.size(); i++)
            indexById.put(tiles.get(i).getId(), i);
    }

    public Board getBoard() {
        return board;
    }

    public void seed(Map<Long, PlayerState> stateByPlayer) {
        List<Tile> tiles = board.getTiles();
        Long startId = tiles.isEmpty() ? null : tiles.get(0).getId();

        if (startId == null) throw new IllegalStateException("Board sem tiles");
        stateByPlayer.values()
                .forEach(ps -> ps.moveTo(startId));
    }

    public void movePlayer(PlayerState ps, Tile destination) {
        ps.moveTo(destination.getId());
    }

    public Tile getTileAtOffset(Long currentTileId, int steps) {
        Integer currentIndex = indexById.get(currentTileId);
        if (currentIndex == null)
            throw new IllegalArgumentException("Tile atual não encontrado: " + currentTileId);
        if (steps < 0)
            throw new IllegalArgumentException("Steps não pode ser negativo: " + steps);

        int destinationIndex = Math.min(currentIndex + steps, board.getTiles().size() - 1);
        return board.getTiles().get(destinationIndex);
    }

    public boolean isLastTile(Long tileId) {
        List<Tile> tiles = board.getTiles();
        if (tiles.isEmpty()) return false;
        return tiles.get(tiles.size() - 1).getId().equals(tileId);
    }
}

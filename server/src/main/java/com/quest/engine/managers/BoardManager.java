package com.quest.engine.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.quest.engine.state.BoardState;
import com.quest.engine.state.PlayerState;
import com.quest.engine.state.TileState;

public class BoardManager {

    private final BoardState boardState;
    private final Map<Long, Integer> indexById;
    private static final Random random = new Random();

    public BoardManager(BoardState state) {
        this.boardState = state;
        this.indexById = new HashMap<>(boardState.getTiles().size());

        List<TileState> tiles = boardState.getTiles();
        for (int i = 0; i < tiles.size(); i++)
            indexById.put(tiles.get(i).getId(), i);
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public void seed(Map<Long, PlayerState> states) {
        Long startId = boardState.getStartTile().getId();
        states.values().forEach(ps -> ps.moveTo(startId));
    }

    public void movePlayer(PlayerState ps, TileState destination) {
        ps.moveTo(destination.getId());
    }

    public TileState getTileAtOffset(Long currentTileId, int steps) {
        Integer currentIndex = indexById.get(currentTileId);
        if (currentIndex == null)
            throw new IllegalArgumentException("Tile atual não encontrado: " + currentTileId);
        // if (steps < 0)
        // throw new IllegalArgumentException("Steps não pode ser negativo: " + steps);

        int destinationIndex;
        if (steps >= 0)
            destinationIndex = Math.min(currentIndex + steps, boardState.getTiles().size() - 1);

        else
            destinationIndex = Math.max(currentIndex + steps, 0);

        return boardState.getTiles().get(destinationIndex);
    }

    public void rollDiceAndMove(PlayerState ps) {
        int steps = random.nextInt(6) + 1;

        System.out.println("Jogador " + ps.getPlayerId() + " rolou o dado e obteve: " + steps);

        Long currentTileId = ps.getCurrentTileId();
        TileState destination = getTileAtOffset(currentTileId, steps);

        movePlayer(ps, destination);
    }

    public void reverseMovement(PlayerState ps) {
        Long currentTileId = ps.getCurrentTileId();
        Integer steps = ps.getPendingSteps() * -1;
        TileState destination = getTileAtOffset(currentTileId, steps);
        movePlayer(ps, destination);
    }

    public boolean isLastTile(Long tileId) {
        List<TileState> tiles = boardState.getTiles();
        if (tiles.isEmpty())
            return false;
        return tiles.get(tiles.size() - 1).getId().equals(tileId);
    }
}

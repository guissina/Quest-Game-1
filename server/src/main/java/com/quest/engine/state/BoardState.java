package com.quest.engine.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.quest.models.Board;
import com.quest.models.Theme;
import com.quest.models.Tile;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class BoardState {

    private final Long id;
    private final String name;
    private final int rows;
    private final int cols;
    private final List<TileState> tiles;

    @JsonProperty("themes")
    private final List<Theme> selectedThemes;

    public BoardState(Long id,
                      String name,
                      int rows,
                      int cols,
                      List<TileState> tiles,
                      List<Theme> selectedThemes) {
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.cols = cols;
        this.selectedThemes = List.copyOf(selectedThemes);
        this.tiles = List.copyOf(tiles);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public List<TileState> getTiles() {
        return tiles;
    }

    public List<Theme> getSelectedThemes() {
        return selectedThemes;
    }

    @JsonIgnore
    public TileState getStartTile() {
        return tiles.stream()
                .filter(tile -> tile.getSequence() == 0)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Start tile not found"));
    }

    public static BoardState create(Board board, List<Theme> selectedThemes) {
        if (selectedThemes.isEmpty())
            throw new IllegalArgumentException("At least one theme must be selected");

        Random random = new Random();
        List<TileState> tileStates = board.getTiles().stream()
                .map(tile -> TileState.create(tile, selectedThemes, random))
                .collect(Collectors.toList());

        return new BoardState(
                board.getId(),
                board.getName(),
                board.getRows(),
                board.getCols(),
                tileStates,
                selectedThemes
        );
    }
}

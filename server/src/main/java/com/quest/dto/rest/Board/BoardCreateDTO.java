package com.quest.dto.rest.Board;

import java.util.List;

import com.quest.dto.rest.tile.TileCreateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BoardCreateDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Rows are required")
    private int rows;

    @NotNull(message = "Cols are required")
    private int cols;

    @NotNull(message = "Tiles are required")
    private List<TileCreateDTO> tiles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public List<TileCreateDTO> getTiles() {
        return tiles;
    }

    public void setTiles(List<TileCreateDTO> tiles) {
        this.tiles = tiles;
    }
}

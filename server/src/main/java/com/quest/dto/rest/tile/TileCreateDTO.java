package com.quest.dto.rest.tile;

import jakarta.validation.constraints.NotNull;

public class TileCreateDTO {

    @NotNull(message = "Tile row index is required")
    private int row;

    @NotNull(message = "Tile column index is required")
    private int col;

    @NotNull(message = "Tile sequence is required")
    private int sequence;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}

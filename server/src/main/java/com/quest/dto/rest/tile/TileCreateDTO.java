package com.quest.dto.rest.tile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TileCreateDTO {
    Long id;

    @NotBlank(message = "Tile name is required")
    private String name;

    @NotNull(message = "Board ID is required")
    private Long boardId;

    @NotNull(message = "Row index is required")
    private int row;

    @NotNull(message = "Column index is required")
    private int col;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

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

    
}

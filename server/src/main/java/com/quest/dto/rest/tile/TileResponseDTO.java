package com.quest.dto.rest.tile;

public class TileResponseDTO {
    private Long id;
    private Long boardId;
    private int row;
    private int col;
    private String specialCard;
    private String questionTheme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSpecialCard() {
        return specialCard;
    }

    public void setSpecialCard(String specialCard) {
        this.specialCard = specialCard;
    }

    public String getQuestionTheme() {
        return questionTheme;
    }

    public void setQuestionTheme(String questionTheme) {
        this.questionTheme = questionTheme;
    }
}

package com.quest.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tiles")
public class Tile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(name = "row_index", nullable = false)
    private int row;

    @Column(name = "col_index", nullable = false)
    private int col;

    @Transient
    private SpecialCard specialCard;

    @Transient
    private Theme questionTheme;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public SpecialCard getSpecialCard() {
        return specialCard;
    }

    public void setSpecialCard(SpecialCard specialCard) {
        this.specialCard = specialCard;
    }

    public Theme getQuestionTheme() {
        return questionTheme;
    }

    public void setQuestionTheme(Theme questionTheme) {
        this.questionTheme = questionTheme;
    }
}

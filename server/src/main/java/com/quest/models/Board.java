package com.quest.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_count", nullable = false)
    private int rows;

    @Column(name = "col_count", nullable = false)
    private int cols;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Tile> tiles = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public Tile initialTile() {
        return tiles.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No tiles found in the board"));
    }

    public Optional<Tile> findTileById(Long id) {
        return tiles.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }
}

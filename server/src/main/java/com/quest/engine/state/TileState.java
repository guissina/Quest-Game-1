package com.quest.engine.state;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quest.models.SpecialCard;
import com.quest.models.Theme;
import com.quest.models.Tile;

public class TileState {

    private final Long id;
    private final int sequence;
    private final int row;
    private final int col;
    private Boolean blocked;

    @JsonProperty("themes")
    private final List<Theme> assignedThemes;

    @JsonProperty("specialCard")
    private final SpecialCard specialCard;

    public TileState(Long id,
            int sequence,
            int row,
            int col,
            List<Theme> assignedThemes,
            SpecialCard specialCard) {
        this.id = id;
        this.sequence = sequence;
        this.row = row;
        this.col = col;
        this.assignedThemes = assignedThemes;
        this.specialCard = specialCard;
        this.blocked = false;
    }

    public Long getId() {
        return id;
    }

    public int getSequence() {
        return sequence;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public List<Theme> getAssignedThemes() {
        return assignedThemes;
    }

    public SpecialCard getSpecialCard() {
        return specialCard;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public static TileState create(Tile tile, Theme theme) {
        List<Theme> themes = List.of(theme);
        return new TileState(
                tile.getId(),
                tile.getSequence(),
                tile.getRow(),
                tile.getCol(),
                themes,
                null);
    }
}

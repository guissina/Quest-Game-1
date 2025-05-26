package com.quest.engine.state;

import com.quest.models.SpecialCard;
import com.quest.models.Theme;
import com.quest.models.Tile;

import java.util.List;
import java.util.Random;

public class TileState {

    private final Long id;
    private final int sequence;
    private final int row;
    private final int col;
    private final Theme assignedTheme;
    private final SpecialCard specialCard;

    public TileState(Long id,
                     int sequence,
                     int row,
                     int col,
                     Theme assignedTheme,
                     SpecialCard specialCard) {
        this.id = id;
        this.sequence = sequence;
        this.row = row;
        this.col = col;
        this.assignedTheme = assignedTheme;
        this.specialCard = specialCard;
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

    public Theme getAssignedTheme() {
        return assignedTheme;
    }

    public SpecialCard getSpecialCard() {
        return specialCard;
    }

    public static TileState create(Tile tile, List<Theme> selectedThemes, Random random) {
        Theme theme = selectedThemes.get(random.nextInt(selectedThemes.size()));
        return new TileState(
                tile.getId(),
                tile.getSequence(),
                tile.getRow(),
                tile.getCol(),
                theme,
                null
        );
    }
}


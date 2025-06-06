package com.quest.engine.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.quest.models.Board;
import com.quest.models.Theme;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        this.tiles = List.copyOf(tiles);
        this.selectedThemes = List.copyOf(selectedThemes);
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
        if (selectedThemes.isEmpty()) {
            throw new IllegalArgumentException("At least one theme must be selected");
        }

        // 1. Coleta todos os tiles originais e ordena por sequência (para atribuir na
        // ordem correta)
        List<com.quest.models.Tile> rawTiles = new ArrayList<>(board.getTiles());
        rawTiles.sort(Comparator.comparingInt(com.quest.models.Tile::getSequence));
        int totalTiles = rawTiles.size();
        int themeCount = selectedThemes.size();

        // 2. Calcula quantos tiles cada tema deve ter (divisão inteira + resto)
        int basePerTheme = totalTiles / themeCount;
        int remainder = totalTiles % themeCount;

        // 3. Monta um mapa {tema -> quantidade a atribuir}
        // Cada tema terá “basePerTheme” aparições, e os primeiros 'remainder' temas
        // ganham +1
        Map<Theme, Integer> themeCounts = new HashMap<>();
        for (int i = 0; i < themeCount; i++) {
            Theme tema = selectedThemes.get(i);
            int count = basePerTheme + (i < remainder ? 1 : 0);
            themeCounts.put(tema, count);
        }

        // 4. Para cada tile, vamos escolher o tema com maior contagem restante que seja
        // diferente do último atribuído
        List<TileState> tileStates = new ArrayList<>(totalTiles);

        Theme ultimoTema = null; // inicialmente, nenhum tema foi atribuído
        for (int i = 0; i < totalTiles; i++) {
            com.quest.models.Tile tile = rawTiles.get(i);

            // 4.1. Filtra os temas que ainda têm contagem > 0
            List<Theme> temasDisponiveis = new ArrayList<>();
            for (Map.Entry<Theme, Integer> entry : themeCounts.entrySet()) {
                if (entry.getValue() > 0) {
                    temasDisponiveis.add(entry.getKey());
                }
            }

            // 4.2. Se houver apenas um tema restante, ele é o escolhido (mesmo que seja
            // igual ao último)
            // Caso contrário, filtra para não repetir o mesmo do tile anterior
            Theme temaSelecionado;
            if (temasDisponiveis.size() == 1) {
                temaSelecionado = temasDisponiveis.get(0);
            } else {
                // Cria uma lista ordenada por contagem decrescente
                temasDisponiveis.sort((a, b) -> themeCounts.get(b).compareTo(themeCounts.get(a)));

                // Tenta pegar o maior que não seja igual ao últimoTema
                if (temasDisponiveis.get(0).equals(ultimoTema)) {
                    // se o mais numeroso for igual ao último, escolhe o segundo da lista
                    temaSelecionado = temasDisponiveis.get(1);
                } else {
                    temaSelecionado = temasDisponiveis.get(0);
                }
            }

            // 4.3. Decrementa a contagem desse tema
            themeCounts.put(temaSelecionado, themeCounts.get(temaSelecionado) - 1);
            ultimoTema = temaSelecionado;

            // 4.4. Cria o TileState passando o tema fixo
            TileState ts = TileState.create(tile, temaSelecionado);
            tileStates.add(ts);
        }

        // 5. Constrói e retorna o estado do board
        return new BoardState(
                board.getId(),
                board.getName(),
                board.getRows(),
                board.getCols(),
                tileStates,
                selectedThemes);
    }
}

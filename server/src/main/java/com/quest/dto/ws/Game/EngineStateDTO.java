package com.quest.dto.ws.Game;

import com.quest.models.Player;
import com.quest.models.Board;
import java.util.List;

public class EngineStateDTO {
    public String gameId;
    public Board  board;
    public List<Player> players;
    public int currentTurn;

    public EngineStateDTO(String gameId, Board board, List<Player> players, int currentTurn) {
        this.gameId = gameId;
        this.board = board;
        this.players = players;
        this.currentTurn = currentTurn;
    }
}

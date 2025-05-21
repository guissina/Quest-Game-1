package com.quest.engine.core;

import com.quest.engine.managers.BoardManager;
import com.quest.engine.managers.QuestionManager;
import com.quest.engine.managers.TurnManager;
import com.quest.engine.state.PlayerState;
import com.quest.models.Player;
import com.quest.models.Board;
import com.quest.models.Question;
import com.quest.models.Tile;

import java.util.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameEngine {

    private final GameRoom room;
    private final BoardManager boardManager;
    private TurnManager turnManager;
    private QuestionManager questionManager;

    private final Map<Long, PlayerState> stateByPlayer = new ConcurrentHashMap<>();

    public GameEngine(GameRoom room, Board board) {
        this.room = room;
        this.boardManager = new BoardManager(board);
    }

    public GameRoom getRoom() {
        return room;
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public Map<Long, PlayerState> getStateByPlayer() {
        return Collections.unmodifiableMap(stateByPlayer);
    }

    public void initializeGameState(int initialTokens) {
        List<Integer> tokenList = IntStream.rangeClosed(1, initialTokens).boxed().toList();
        for (Player p : room.getPlayers()) {
            stateByPlayer.put(p.getId(), new PlayerState(p.getId(), tokenList, null));
        }
        List<PlayerState> states = new ArrayList<>(stateByPlayer.values());
        this.turnManager = new TurnManager(states);
        this.questionManager = new QuestionManager(stateByPlayer);
        room.markStarted();
    }

    public void seed() {
        boardManager.seed(stateByPlayer);
    }

    public boolean answerQuestion(Long playerId, Question question, Long optionId, int steps) {
        turnManager.verifyTurn(playerId);
        boolean correct = questionManager.handleAnswer(playerId, question, optionId, steps);
        turnManager.nextTurn();
        return correct;
    }

    public void move(Long playerId, Long tileId) {
        turnManager.verifyTurn(playerId);
        questionManager.verifyCanMove(playerId);

        PlayerState ps = stateByPlayer.get(playerId);
        if (ps == null) throw new RuntimeException("Player not found.");

        Tile tile = boardManager.getBoard().findTileById(tileId)
                .orElseThrow(() -> new RuntimeException("Tile not found."));

        boardManager.movePlayer(ps, tile);
    }
}

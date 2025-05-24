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
import java.util.stream.IntStream;

public class GameEngine {

    private final BoardManager boardManager;
    private final QuestionManager questionManager;
    private final TurnManager turnManager;
    private final Map<Long, PlayerState> stateByPlayer = new ConcurrentHashMap<>();

    private boolean finished = false;
    private Long winnerId = null;

    public GameEngine(List<Player> players, Board board, int initialTokens) {
        this.boardManager = new BoardManager(board);
        this.questionManager = new QuestionManager();

        List<Integer> tokenList = IntStream.rangeClosed(1, initialTokens).boxed().toList();
        for (Player p : players) {
            stateByPlayer.put(p.getId(), new PlayerState(p.getId(), tokenList, null));
        }
        this.turnManager = new TurnManager(new ArrayList<>(stateByPlayer.values()));
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

    public boolean isFinished() {
        return finished;
    }

    public Optional<Long> getWinnerId() {
        return Optional.ofNullable(winnerId);
    }

    public void seed() {
        boardManager.seed(stateByPlayer);
    }

    public void move(PlayerState playerState, int steps) {
        if (playerState == null)
            throw new RuntimeException("Player not found.");
        Tile tile = boardManager.getTileAtOffset(playerState.getCurrentTileId(), steps);
        boardManager.movePlayer(playerState, tile);
    }

    public void registerQuestionFor(Long playerId, Question question) {
        turnManager.verifyTurn(playerId);
        questionManager.markUsed(question.getId());

        PlayerState ps = stateByPlayer.get(playerId);
        if (ps == null) throw new IllegalArgumentException("Player not found");
        ps.setPendingQuestion(question);
    }

    public boolean hasUsedQuestion(Long questionId) {
        return questionManager.hasUsed(questionId);
    }

    public void answerQuestion(Long playerId, Long selectedOptionId, int steps) {
        turnManager.verifyTurn(playerId);

        PlayerState ps = stateByPlayer.get(playerId);
        if (ps == null) throw new IllegalArgumentException("Player not found");

        boolean wasAtLast = boardManager.isLastTile(ps.getCurrentTileId());
        boolean correct = questionManager.processAnswer(ps, selectedOptionId, steps);

        if (correct)
            move(ps, steps);
        if (wasAtLast && correct) {
            this.finished = true;
            this.winnerId = playerId;
        }

        ps.clearPendingQuestion();
        turnManager.nextTurn();
    }
}

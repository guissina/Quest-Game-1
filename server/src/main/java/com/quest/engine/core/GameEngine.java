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

    private final List<Integer> initialTokensList;
    private boolean finished = false;
    private Long winnerId = null;

    public GameEngine(List<Player> players, Board board, int initialTokens) {
        this.boardManager = new BoardManager(board);
        this.questionManager = new QuestionManager();
        this.initialTokensList = IntStream.rangeClosed(1, initialTokens).boxed().toList();

        List<PlayerState> playerStates = new ArrayList<>();
        for (Player p : players) {
            PlayerState ps = new PlayerState(p.getId(), this.initialTokensList, null);
            stateByPlayer.put(p.getId(), ps);
            playerStates.add(ps);
        }
        this.turnManager = new TurnManager(playerStates);
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public Map<Long, PlayerState> getAllPlayerStates() {
        return Collections.unmodifiableMap(stateByPlayer);
    }

    public void joinGame(Player player) {
        PlayerState ps = new PlayerState(player.getId(), this.initialTokensList,
                boardManager.getBoard().getStartTile().getId());
        stateByPlayer.put(player.getId(), ps);
        turnManager.enqueuePlayer(ps);
    }

    public void leaveGame(Long playerId) {
        stateByPlayer.remove(playerId);
        turnManager.dequeuePlayer(playerId);
        if (stateByPlayer.isEmpty()) {
            finished = false;
            winnerId = null;
        }
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

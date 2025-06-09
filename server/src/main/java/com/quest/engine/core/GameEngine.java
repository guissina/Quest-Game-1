package com.quest.engine.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import com.quest.engine.managers.BoardManager;
import com.quest.engine.managers.QuestionManager;
import com.quest.engine.managers.TurnManager;
import com.quest.engine.state.BoardState;
import com.quest.engine.state.PlayerState;
import com.quest.engine.state.TileState;
import com.quest.enums.AbilityType;
import com.quest.models.Player;
import com.quest.models.Question;
import com.quest.models.Theme;

public class GameEngine {

    private final BoardManager boardManager;
    private final QuestionManager questionManager;
    private final TurnManager turnManager;
    private final Map<Long, PlayerState> stateByPlayer = new ConcurrentHashMap<>();

    private final List<Integer> initialTokensList;
    private boolean finished = false;
    private Long winnerId = null;

    public GameEngine(List<Player> players, BoardState boardState, int initialTokens) {
        this.boardManager = new BoardManager(boardState);
        this.questionManager = new QuestionManager();
        this.initialTokensList = IntStream.rangeClosed(1, initialTokens).boxed().toList();

        List<PlayerState> playerStates = new ArrayList<>();
        for (Player p : players) {
            PlayerState ps = new PlayerState(p.getId(), this.initialTokensList, null, 0);
            stateByPlayer.put(p.getId(), ps);
            playerStates.add(ps);
        }
        this.turnManager = new TurnManager(playerStates);
    }

    public PlayerState getPlayerState(Long playerId) {
        return stateByPlayer.get(playerId);
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
                boardManager.getBoardState().getStartTile().getId(), 0);
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

    public void move(PlayerState playerState) {
        if (playerState == null)
            throw new RuntimeException("Player not found.");

        if (playerState.isAbilityActive(AbilityType.ROLL_DICE)) {
            playerState.removeAbility(AbilityType.ROLL_DICE);
            boardManager.rollDiceAndMove(playerState);
            return;
        }

        // boolean anyPlayerHasReverseMovement = stateByPlayer.values().stream()
        // .anyMatch(ps -> ps.isAbilityActive(AbilityType.REVERSE_MOVEMENT));

        // if (anyPlayerHasReverseMovement) {
        // PlayerState playerWithReverseMovement = stateByPlayer.values().stream()
        // .filter(ps -> ps.isAbilityActive(AbilityType.REVERSE_MOVEMENT))
        // .findFirst()
        // .orElse(null);

        // if (playerWithReverseMovement != null) {
        // applyReverseMovementEffect();
        // boardManager.reverseMovement(playerState);
        // return;
        // }

        // }

        TileState tile = boardManager.getTileAtOffset(playerState.getCurrentTileId(), playerState.getPendingSteps());
        boardManager.movePlayer(playerState, tile);
    }

    public void prepareQuestion(Long playerId, Question question, int steps) {
        turnManager.verifyTurn(playerId);

        PlayerState ps = Optional.ofNullable(stateByPlayer.get(playerId))
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        ps.setPendingSteps(steps);
        ps.setPendingQuestion(question);
        questionManager.markUsed(question.getId());
    }

    public boolean hasUsedQuestion(Long questionId) {
        return questionManager.hasUsed(questionId);
    }

    public void answerQuestion(Long playerId, Long selectedOptionId) {
        turnManager.verifyTurn(playerId);
        PlayerState ps = Optional.ofNullable(stateByPlayer.get(playerId))
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        boolean wasAtLast = boardManager.isLastTile(ps.getCurrentTileId());
        boolean hadSkipAbility = ps.isAbilityActive(AbilityType.SKIP_OPPONENT_TURN);

        boolean correct = questionManager.processAnswer(ps, selectedOptionId);

        applyMovementOrReset(ps, correct);

        if (correct && wasAtLast) {
            finished = true;
            winnerId = playerId;
        }

        if (correct)
            applyStreakReward(ps);

        if (ps.isAbilityActive(AbilityType.RETURN_TILE) && correct) {
            ps.removeAbility(AbilityType.RETURN_TILE);
            applyReturnTileEffect();
        }

        if (hadSkipAbility && correct) {
            System.out.println("Player " + playerId + " used SKIP_OPPONENT_TURN ability.");

            ps.removeAbility(AbilityType.SKIP_OPPONENT_TURN);
            turnManager.skipNextTurn();
            ps.clearPendingQuestionAndSteps();
            return;
        }

        if (ps.isAbilityActive(AbilityType.RETURN_TILE) && correct) {
            ps.removeAbility(AbilityType.RETURN_TILE);
            applyReturnTileEffect();
        }

        // if (ps.isAbilityActive(AbilityType.REVERSE_MOVEMENT) && correct) {
        // ps.removeAbility(AbilityType.REVERSE_MOVEMENT);
        // applyReverseMovementEffect();
        // }

        ps.clearPendingQuestionAndSteps();
        turnManager.nextTurn();
    }

    private void applyReturnTileEffect() {
        turnManager.nextTurn();
        Long nextPlayerId = turnManager.getCurrentPlayerId();
        PlayerState nextPlayer = stateByPlayer.get(nextPlayerId);

        if (nextPlayer != null) {
            Long currentTileId = nextPlayer.getCurrentTileId();
            TileState previousTile = boardManager.getTileAtOffset(currentTileId, -1);
            boardManager.movePlayer(nextPlayer, previousTile);
        }

        turnManager.nextTurn();
    }

    // TODO melhorar a lógica de movimento reverso
    private void applyReverseMovementEffect() {
        turnManager.nextTurn();
        Long nextPlayerId = turnManager.getCurrentPlayerId();
        PlayerState nextPlayer = stateByPlayer.get(nextPlayerId);

        if (nextPlayer != null) {
            // Marca o próximo jogador para ter movimento reverso
            nextPlayer.activateAbility(AbilityType.REVERSE_MOVEMENT);
            System.out.println("Player " + nextPlayerId + " terá movimento reverso no próximo turno");
        }

        turnManager.nextTurn();
    }

    private void applyMovementOrReset(PlayerState ps, boolean correct) {
        if (correct)
            move(ps);
        else if (ps.getTokens().isEmpty()) {
            Long startId = boardManager.getBoardState().getStartTile().getId();
            ps.moveTo(startId); // Move para a casa inicial
            ps.resetTokens(initialTokensList);
        }
    }

    // TODO Implementar lógicas

    // TODO melhorar a lógica de recompensas
    public void applyStreakReward(PlayerState ps) {
        int streak = ps.getCorrectCount();
        switch (streak) {
            case 3 -> ps.addToken(2);
            case 5 -> ps.addToken(3);
            case 7 -> ps.addToken(4);
        }
    }

    public void forceFailQuestion(Long playerId) {
        PlayerState ps = Optional.ofNullable(stateByPlayer.get(playerId))
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));
        questionManager.processFail(ps);
        ps.clearPendingQuestionAndSteps();
        turnManager.nextTurn();
    }

    public void forceSkipTurn(Long playerId) {
        PlayerState ps = stateByPlayer.get(playerId);
        if (ps != null)
            ps.clearPendingQuestionAndSteps();
        turnManager.nextTurn();
    }
}

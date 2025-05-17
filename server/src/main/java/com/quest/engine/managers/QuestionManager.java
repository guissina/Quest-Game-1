package com.quest.engine.managers;

import com.quest.engine.state.PlayerState;
import com.quest.models.Question;
import com.quest.models.QuestionOption;

import java.util.Map;
import java.util.Optional;

public class QuestionManager {
    private final Map<Long, PlayerState> stateByPlayer;

    public QuestionManager(Map<Long, PlayerState> stateByPlayer) {
        this.stateByPlayer = stateByPlayer;
    }

    public boolean handleAnswer(Long playerId, Question question, Long optionId, int steps) {
        PlayerState ps = stateByPlayer.get(playerId);

        Optional<QuestionOption> option = question.getOptionById(optionId);
        if (option.isEmpty())
            throw new RuntimeException("Opção inválida");
        boolean correct = option.get().getCorrect();

        if (correct)
            ps.setMustAnswerBeforeMoving(false);
        else {
            if (!ps.isMustAnswerBeforeMoving())
                ps.consumeTokens(steps);
            ps.setMustAnswerBeforeMoving(true);
        }
        return correct;
    }

    public void verifyCanMove(Long playerId) {
        PlayerState ps = stateByPlayer.get(playerId);
        if (ps == null)
            throw new RuntimeException("Player not found.");
        if (ps.isMustAnswerBeforeMoving())
            throw new RuntimeException("You must answer before moving.");
    }
}

package com.quest.engine.managers;

import com.quest.engine.state.PlayerState;
import com.quest.models.Question;
import com.quest.models.QuestionOption;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class QuestionManager {

    private final Set<Long> usedQuestionIds = ConcurrentHashMap.newKeySet();

    public QuestionManager() {}

    public void markUsed(Long questionId) {
        usedQuestionIds.add(questionId);
    }

    public boolean hasUsed(Long questionId) {
        return usedQuestionIds.contains(questionId);
    }

    private void handleIncorrect(PlayerState ps, int steps) {
        ps.consumeTokens(steps);
    }

    public boolean processAnswer(PlayerState ps, Long selectedOptionId, int steps) {
        Question question = ps.getPendingQuestion();
        if (question == null)
            throw new IllegalStateException("Nenhuma pergunta pendente");

        QuestionOption option = question.getOptionById(selectedOptionId)
                .orElseThrow(() -> new IllegalStateException("Invalid optionId: " + selectedOptionId));

        boolean correct = option.getCorrect();
        if (!correct)
            handleIncorrect(ps, steps);
        return correct;
    }

    public void processFail(PlayerState ps, int steps) {
        handleIncorrect(ps, steps);
    }
}

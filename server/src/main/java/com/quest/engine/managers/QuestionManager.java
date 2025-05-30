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

    public boolean processAnswer(PlayerState ps, Long selectedOptionId, int steps) {
        Question question = ps.getPendingQuestion();
        if (question == null)
            throw new IllegalStateException("Nenhuma pergunta pendente");

        QuestionOption option = question.getOptionById(selectedOptionId)
                .orElseThrow(() -> new IllegalStateException("Invalid optionId: " + selectedOptionId) );

        markUsed(question.getId()); // TODO AVALIAR REDUNDANCY
        boolean correct = option.getCorrect();
        if (!correct)
            ps.consumeTokens(steps);
        return correct;
    }
}

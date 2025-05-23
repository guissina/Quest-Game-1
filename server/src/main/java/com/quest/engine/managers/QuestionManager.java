package com.quest.engine.managers;

import com.quest.engine.state.PlayerState;
import com.quest.models.Question;
import com.quest.models.QuestionOption;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class QuestionManager {

    private final Set<Long> usedQuestionIds = ConcurrentHashMap.newKeySet();

    public QuestionManager() {}

    public boolean hasUsed(Long questionId) {
        return usedQuestionIds.contains(questionId);
    }

    public void markUsed(Long questionId) {
        usedQuestionIds.add(questionId);
    }

    public boolean processAnswer(PlayerState ps, Long selectedOptionId, int steps) {
        Question question = ps.getPendingQuestion();
        if (question == null)
            throw new IllegalStateException("No pending question");

        Optional<QuestionOption> option = question.getOptionById(selectedOptionId);
        if (option.isEmpty())
            throw new IllegalArgumentException("Invalid optionId");

        boolean correct = option.get().getCorrect();
        if (!correct)
            ps.consumeToken(steps);
        return correct;
    }
}

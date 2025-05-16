package com.quest.dto.rest.questionOptions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuestionOptionUpdateDTO {
    long id;

    @NotBlank(message = "Option text is required")
    private String optionText;

    @NotNull(message = "Correct answer is required")
    private boolean isCorrect;

    @NotNull(message = "Question ID is required")
    private Long questionId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}

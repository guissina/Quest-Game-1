package com.quest.dto.rest.questionOptions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuestionOptionUpdateDTO {
    long id;

    @NotBlank(message = "Option text is required")
    private String optionText;

    @NotNull(message = "Correct answer is required")
    private Boolean correct;

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

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}

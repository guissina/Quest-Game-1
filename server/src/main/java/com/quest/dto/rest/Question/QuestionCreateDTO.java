package com.quest.dto.rest.Question;

import com.quest.enums.Difficulty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class QuestionCreateDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String questionText;

    @NotBlank(message = "Question text is required")
    @Size(min = 1, max = 500, message = "Question text must be between 1 and 500 characters")
    private String answer;

    @NotNull(message = "Theme is required")
    private long themeId;

    @NotNull(message = "Difficulty is required")
    private Difficulty difficulty;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public long getThemeId() {
        return themeId;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }

}

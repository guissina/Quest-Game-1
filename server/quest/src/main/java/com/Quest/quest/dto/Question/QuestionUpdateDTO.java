package com.Quest.quest.dto.Question;

import com.Quest.quest.enums.Difficulty;
import com.Quest.quest.enums.Themes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class QuestionUpdateDTO {
    private long id;

    @NotBlank(message = "Question text is required")
    @Size(min = 1, max = 500, message = "Question text must be between 1 and 500 characters")
    private String questionText;

    @NotBlank(message = "Answer is required")
    @Size(min = 1, max = 255, message = "Answer must be between 1 and 255 characters")
    private String answer;

    @NotBlank(message = "Difficulty is required")
    @Size(min = 1, max = 50, message = "Difficulty must be between 1 and 50 characters")
    private Difficulty difficulty;

    @NotBlank(message = "Theme is required")
    @Size(min = 1, max = 100, message = "Theme must be between 1 and 100 characters")
    private Themes themes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Themes getThemes() {
        return themes;
    }

    public void setThemes(Themes themes) {
        this.themes = themes;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}

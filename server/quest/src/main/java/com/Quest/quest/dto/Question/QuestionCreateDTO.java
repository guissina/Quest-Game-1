package com.Quest.quest.dto.Question;

import com.Quest.quest.enums.Difficulty;
import com.Quest.quest.enums.Themes;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class QuestionCreateDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Column(name = "title", length = 255, nullable = false)
    private String questionText;

    @NotBlank(message = "Question text is required")
    @Size(min = 1, max = 500, message = "Question text must be between 1 and 500 characters")
    @Column(name = "question_text", length = 500, nullable = false)
    private String answer;

    @NotNull(message = "Theme is required")
    @Size(min = 1, max = 100, message = "Theme must be between 1 and 100 characters")
    @Column(name = "themes", nullable = false)
    private Themes themes;

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

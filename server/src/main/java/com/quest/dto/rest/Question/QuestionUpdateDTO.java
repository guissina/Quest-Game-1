package com.quest.dto.rest.Question;

import java.util.ArrayList;
import java.util.List;

import com.quest.dto.rest.questionOptions.QuestionOptionCreateDTO;
import com.quest.enums.Difficulty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class QuestionUpdateDTO {
    private long id;

    @NotBlank(message = "Question text is required")
    @Size(min = 1, max = 500, message = "Question text must be between 1 and 500 characters")
    private String questionText;

    @NotBlank(message = "Difficulty is required")
    @Size(min = 1, max = 50, message = "Difficulty must be between 1 and 50 characters")
    private Difficulty difficulty;

    @NotNull(message = "Theme is required")
    private long themeId;

    @NotNull(message = "Options are required")
    @Size(min = 2, message = "At least two options are required")
    private List<QuestionOptionCreateDTO> options = new ArrayList<>();

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

    public List<QuestionOptionCreateDTO> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOptionCreateDTO> options) {
        this.options = options;
    }
}

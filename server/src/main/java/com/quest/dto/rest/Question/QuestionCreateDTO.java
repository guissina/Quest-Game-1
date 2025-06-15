package com.quest.dto.rest.Question;

import java.util.ArrayList;
import java.util.List;

import com.quest.dto.rest.questionOptions.QuestionOptionCreateDTO;
import com.quest.enums.Difficulty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class QuestionCreateDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String questionText;

    //TODO Rever isso aqui por causa do QuestionsWithTheme @NotNull(message = "Theme is required")
    private long themeId;

    @NotNull(message = "Difficulty is required")
    private Difficulty difficulty;

    @NotNull(message = "Options are required")
    @Size(min = 2, message = "At least two options are required")
    private List<QuestionOptionCreateDTO> options = new ArrayList<>();

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

    public void setOptions(List<QuestionOptionCreateDTO> options) {
        this.options = options;
    }

    public List<QuestionOptionCreateDTO> getOptions() {
        return options;
    }

}

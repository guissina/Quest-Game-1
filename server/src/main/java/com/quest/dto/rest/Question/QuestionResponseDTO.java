package com.quest.dto.rest.Question;

import java.util.List;

import com.quest.dto.rest.questionOptions.QuestionOptionResponseDTO;

public class QuestionResponseDTO {
    private Long id;
    private String questionText;
    private List<QuestionOptionResponseDTO> options;
    private String difficulty;
    private long themeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public long getThemeId() {
        return themeId;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }

    public List<QuestionOptionResponseDTO> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOptionResponseDTO> options) {
        this.options = options;
    }
}

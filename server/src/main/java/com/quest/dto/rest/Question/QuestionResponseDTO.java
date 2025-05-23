package com.quest.dto.rest.Question;

import java.util.List;

import com.quest.dto.rest.questionOptions.QuestionOptionResponseDTO;
import com.quest.enums.Difficulty;
import com.quest.models.Question;

public class QuestionResponseDTO {
    private Long id;
    private String questionText;
    private List<QuestionOptionResponseDTO> options;
    private Difficulty difficulty;
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

    public List<QuestionOptionResponseDTO> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOptionResponseDTO> options) {
        this.options = options;
    }

    public static QuestionResponseDTO from(Question question) {
        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setId(question.getId());
        dto.setQuestionText(question.getQuestionText());
        dto.setDifficulty(question.getDifficulty());
        dto.setThemeId(question.getTheme().getId());
        dto.setOptions(question.getOptions().stream().map(QuestionOptionResponseDTO::from).toList());
        return dto;
    }
}

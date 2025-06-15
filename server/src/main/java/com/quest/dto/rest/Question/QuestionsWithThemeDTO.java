package com.quest.dto.rest.Question;

import com.quest.dto.rest.Theme.ThemeCreateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class QuestionsWithThemeDTO {

    @Valid
    @NotNull
    private ThemeCreateDTO theme;

    @Valid
    @NotEmpty
    private List<QuestionCreateDTO> questions;

    public QuestionsWithThemeDTO() {
    }

    public QuestionsWithThemeDTO(ThemeCreateDTO theme, List<QuestionCreateDTO> questions) {
        this.theme = theme;
        this.questions = questions;
    }

    public ThemeCreateDTO getTheme() {
        return theme;
    }

    public void setTheme(ThemeCreateDTO theme) {
        this.theme = theme;
    }

    public List<QuestionCreateDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionCreateDTO> questions) {
        this.questions = questions;
    }
}

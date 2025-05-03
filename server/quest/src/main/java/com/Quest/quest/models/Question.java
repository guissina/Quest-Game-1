package com.Quest.quest.models;

import com.Quest.quest.enums.Difficulty;
import com.Quest.quest.enums.Themes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Question text is required")
    @Size(min = 1, max = 500, message = "Question text must be between 1 and 255 characters")
    @Column(name = "question_text", length = 255, nullable = false)
    private String questionText;

    @NotBlank(message = "Answer is required")
    @Size(min = 1, max = 255, message = "Answer must be between 1 and 255 characters")
    @Column(name = "answer", nullable = false)
    private String answer;

    @NotBlank(message = "Theme is required")
    @Size(min = 1, max = 100, message = "Category must be between 1 and 100 characters")
    @Column(name = "themes", nullable = false)
    private Themes themes;

    @NotNull(message = "Difficulty is required")
    @Column(name = "difficulty", nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

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

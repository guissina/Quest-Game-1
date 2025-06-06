package com.quest.dto.rest.questionOptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quest.models.QuestionOption;

public class QuestionOptionResponseDTO {
    private Long id;
    @JsonProperty("text")
    private String optionText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public static QuestionOptionResponseDTO from(QuestionOption option) {
        QuestionOptionResponseDTO dto = new QuestionOptionResponseDTO();
        dto.setId(option.getId());
        dto.setOptionText(option.getOptionText());
        return dto;
    }
}

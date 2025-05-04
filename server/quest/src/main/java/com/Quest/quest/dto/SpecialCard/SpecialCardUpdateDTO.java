package com.Quest.quest.dto.SpecialCard;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SpecialCardUpdateDTO {
    private long id;

    @NotBlank(message = "Card name is required")
    @Size(min = 1, max = 100, message = "Card name must be between 1 and 100 characters")
    private String cardName;

    @NotBlank(message = "Card description is required")
    @Size(min = 1, max = 255, message = "Card description must be between 1 and 255 characters")
    private String cardDescription;

    @NotBlank(message = "Effect is required")
    @Size(min = 1, max = 50, message = "Effect must be between 1 and 50 characters")
    private String effect;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

}

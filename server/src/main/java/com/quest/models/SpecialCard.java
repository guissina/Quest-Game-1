package com.quest.models;

import com.quest.enums.SpecialtyType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "special_cards")
public class SpecialCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Card name is required")
    @Size(min = 1, max = 100, message = "Card name must be between 1 and 100 characters")
    private String cardName;

    @NotBlank(message = "Card description is required")
    @Size(min = 1, max = 255, message = "Card description must be between 1 and 255 characters")
    private String cardDescription;

    @NotBlank(message = "Effect is required")
    @Size(min = 1, max = 50, message = "Card type must be between 1 and 50 characters")
    private String effect;

    @Enumerated(EnumType.STRING)
    private SpecialtyType specialtyType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public SpecialtyType getSpecialtyType() {
        return specialtyType;
    }

    public void setSpecialtyType(SpecialtyType specialtyType) {
        this.specialtyType = specialtyType;
    }

}

package com.quest.enums;

public enum Difficulty {
    EASY("Facil"),
    MEDIUM("Medio"),
    HARD("Dificil");

    private final String description;

    Difficulty(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
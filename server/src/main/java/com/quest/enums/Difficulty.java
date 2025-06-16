package com.quest.enums;

public enum Difficulty {
    EASY("Fácil", 40),
    MEDIUM("Médio", 60),
    HARD("Difícil", 80);

    private final String description;
    private final int timeLimitInSeconds;

    Difficulty(String description, int timeLimitInSeconds) {
        this.description = description;
        this.timeLimitInSeconds = timeLimitInSeconds;
    }

    public String getDescription() {
        return description;
    }

    public int getTimeLimitInSeconds() {
        return timeLimitInSeconds;
    }
}

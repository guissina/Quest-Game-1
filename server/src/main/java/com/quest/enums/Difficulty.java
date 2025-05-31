package com.quest.enums;

public enum Difficulty {
    EASY("Fácil", 30),
    MEDIUM("Médio", 40),
    HARD("Difícil", 60);

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

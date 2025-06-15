package com.quest.dto.ws.Game;

public record TimerDTO(
        Long playerId,
        int secondsLeft,
        String timerType
) { }

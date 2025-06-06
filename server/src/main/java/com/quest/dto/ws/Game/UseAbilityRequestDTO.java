package com.quest.dto.ws.Game;

import com.quest.enums.AbilityType;

public record UseAbilityRequestDTO(
        Long playerId,
        AbilityType abilityType) {
}
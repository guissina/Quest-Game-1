package com.quest.dto.rest.Player;

import jakarta.validation.constraints.NotNull;

public class PlayerThemesDTO {
    @NotNull(message = "Theme ID is required")
    private Long themeId;

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

}

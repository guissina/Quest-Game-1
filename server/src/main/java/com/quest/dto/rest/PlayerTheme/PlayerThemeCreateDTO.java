package com.quest.dto.rest.PlayerTheme;

import jakarta.validation.constraints.NotNull;

public class PlayerThemeCreateDTO {

        @NotNull(message = "Player ID cannot be null")
        private Long playerId;

        @NotNull(message = "Theme ID cannot be null")
        private Long themeId;

        public Long getPlayerId() {
                return playerId;
        }

        public void setPlayerId(Long playerId) {
                this.playerId = playerId;
        }

        public Long getThemeId() {
                return themeId;
        }

        public void setThemeId(Long themeId) {
                this.themeId = themeId;
        }

}

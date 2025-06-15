package com.quest.dto.rest.Player;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;

public class PlayerThemesDTO {
    @NotNull(message = "Theme ID is required")
    private Long themeId;

    @NotNull(message = "Balance is required")
    private BigDecimal balance;

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

}

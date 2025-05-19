package com.quest.dto.rest.Player;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public class PlayerBalanceDTO {
    @NotNull(message = "Balance is required")
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}

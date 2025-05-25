package com.quest.dto.rest.Player;

import java.math.BigDecimal;
import java.util.List;

public class PlayerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Long> themeIds;
    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Long> getThemeIds() {
        return themeIds;
    }

    public void setThemeIds(List<Long> themeIds) {
        this.themeIds = themeIds;
    }
}

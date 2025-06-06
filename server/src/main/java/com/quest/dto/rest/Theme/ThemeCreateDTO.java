package com.quest.dto.rest.Theme;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ThemeCreateDTO {
    @NotBlank(message = "Theme name is required")
    @Size(min = 2, max = 30, message = "Theme name must be between 2 and 30 characters")
    private String name;

    @NotBlank(message = "Theme Code is required")
    @Size(min = 2, max = 30, message = "Theme Code must be between 2 and 30 characters")
    private String code;

    @NotNull(message = "Must specify if the theme is free or paid")
    private Boolean free;

    @NotNull(message = "Theme cost is required")
    private BigDecimal cost;

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Boolean isFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

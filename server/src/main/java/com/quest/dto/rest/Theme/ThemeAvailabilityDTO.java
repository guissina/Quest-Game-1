package com.quest.dto.rest.Theme;

import jakarta.validation.constraints.NotNull;

public class ThemeAvailabilityDTO {
    @NotNull(message = "Availability boolean is required")
    private Boolean free;

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

}

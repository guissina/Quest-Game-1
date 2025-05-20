package com.quest.dto.rest.Player;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PlayerLoginDTO {
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters")
    private String email;

    @NotBlank(message = "Password is required")
    String password;

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

}

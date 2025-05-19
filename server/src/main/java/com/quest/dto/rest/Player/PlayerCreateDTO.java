package com.quest.dto.rest.Player;

import java.math.BigDecimal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PlayerCreateDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "A senha deve conter pelo menos 8 caracteres, uma letra maiúscula, "
            +
            "uma letra minúscula, um número e um caractere especial")
    private String password;

    @NotNull(message = "Balance is required")
    private BigDecimal balance;

    // @NotEmpty(message = "É necessário informar ao menos um boardId")
    // private List<@NotNull(message = "boardId não pode ser nulo") Long> boardIds;

    // public List<Long> getBoardIds() {
    // return boardIds;
    // }

    // public void setBoardIds(List<Long> boardIds) {
    // this.boardIds = boardIds;
    // }

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

}

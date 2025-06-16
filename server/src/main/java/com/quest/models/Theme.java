package com.quest.models;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "themes")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Theme name is required")
    @Size(min = 2, max = 30, message = "Theme name must be between 2 and 30 characters")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Theme Code is required")
    @Size(min = 2, max = 30, message = "Theme Code must be between 2 and 30 characters")
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotBlank
    @Size(min = 2, message = "Theme description have more than 2 characters")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Must specify if the theme is free or paid")
    @Column(name = "is_free", nullable = false)
    private Boolean free;

    @NotNull(message = "Theme cost is required")
    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    @JsonIgnore
    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerTheme> playerThemes;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PlayerTheme> getPlayerThemes() {
        return playerThemes;
    }

    public void setPlayerThemes(List<PlayerTheme> playerThemes) {
        this.playerThemes = playerThemes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Boolean isFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public Boolean getFree() {
        return free;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

}

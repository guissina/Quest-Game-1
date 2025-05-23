package com.quest.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quest.dto.rest.Theme.ThemeCreateDTO;
import com.quest.dto.rest.Theme.ThemeResponseDTO;
import com.quest.dto.rest.Theme.ThemeUpdateDTO;
import com.quest.services.rest.ThemeServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/themes")
@Tag(name = "Theme", description = "Theme management")
public class ThemeController {
    private final ThemeServices themeServices;

    @Autowired
    public ThemeController(ThemeServices themeServices) {
        this.themeServices = themeServices;
    }

    @PostMapping
    @Operation(summary = "Create a new theme")
    public ResponseEntity<ThemeResponseDTO> createTheme(@RequestBody ThemeCreateDTO themeCreateDTO) {
        ThemeResponseDTO createdTheme = themeServices.create(themeCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTheme);
    }

    @PostMapping("/many")
    @Operation(summary = "Create multiple themes")
    public ResponseEntity<List<ThemeResponseDTO>> createManyThemes(
            @RequestBody List<ThemeCreateDTO> themeCreateDTOs) {
        List<ThemeResponseDTO> createdThemes = themeServices.createMany(themeCreateDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdThemes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing theme")
    public ResponseEntity<ThemeResponseDTO> updateTheme(@PathVariable long id,
            @Valid @RequestBody ThemeUpdateDTO themeUpdateDTO) {
        themeUpdateDTO.setId(id);
        ThemeResponseDTO updatedTheme = themeServices.update(themeUpdateDTO);
        return ResponseEntity.ok(updatedTheme);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a theme by ID")
    public ResponseEntity<ThemeResponseDTO> getThemeById(@PathVariable("id") long id) {
        ThemeResponseDTO theme = themeServices.findById(id);
        return ResponseEntity.ok(theme);
    }

    @GetMapping
    @Operation(summary = "Get all themes")
    public ResponseEntity<List<ThemeResponseDTO>> getAllThemes() {
        List<ThemeResponseDTO> themes = themeServices.findAll();
        return ResponseEntity.ok(themes);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get a theme by name")
    public ResponseEntity<ThemeResponseDTO> getThemeByName(@PathVariable("name") String name) {
        ThemeResponseDTO theme = themeServices.findByName(name);
        return ResponseEntity.ok(theme);
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get a theme by code")
    public ResponseEntity<ThemeResponseDTO> getThemeByCode(@PathVariable("code") String code) {
        ThemeResponseDTO theme = themeServices.findByCode(code);
        return ResponseEntity.ok(theme);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a theme by ID")
    public ResponseEntity<Void> deleteTheme(@PathVariable long id) {
        themeServices.delete(id);
        return ResponseEntity.noContent().build();
    }

}

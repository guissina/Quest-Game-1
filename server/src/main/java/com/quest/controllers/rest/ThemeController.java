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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/themes")
public class ThemeController {
    private final ThemeServices themeServices;

    @Autowired
    public ThemeController(ThemeServices themeServices) {
        this.themeServices = themeServices;
    }

    @PostMapping
    public ResponseEntity<ThemeResponseDTO> createTheme(@RequestBody ThemeCreateDTO themeCreateDTO) {
        ThemeResponseDTO createdTheme = themeServices.create(themeCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTheme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThemeResponseDTO> updateTheme(@PathVariable long id,
            @Valid @RequestBody ThemeUpdateDTO themeUpdateDTO) {
        themeUpdateDTO.setId(id);
        ThemeResponseDTO updatedTheme = themeServices.update(themeUpdateDTO);
        return ResponseEntity.ok(updatedTheme);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeResponseDTO> getThemeById(@PathVariable("id") long id,
            @Valid @RequestBody ThemeUpdateDTO themeUpdateDTO) {

        themeUpdateDTO.setId(id);
        ThemeResponseDTO theme = themeServices.findById(themeUpdateDTO.getId());
        return ResponseEntity.ok(theme);
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponseDTO>> getAllThemes() {
        List<ThemeResponseDTO> themes = themeServices.findAll();
        return ResponseEntity.ok(themes);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ThemeResponseDTO> getThemeByName(@PathVariable("name") String name) {
        ThemeResponseDTO theme = themeServices.findByName(name);
        return ResponseEntity.ok(theme);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ThemeResponseDTO> getThemeByCode(@PathVariable("code") String code) {
        ThemeResponseDTO theme = themeServices.findByCode(code);
        return ResponseEntity.ok(theme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable long id) {
        themeServices.delete(id);
        return ResponseEntity.noContent().build();
    }

}

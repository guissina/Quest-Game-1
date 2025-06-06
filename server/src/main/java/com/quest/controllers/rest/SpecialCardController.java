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

import com.quest.dto.rest.SpecialCard.SpecialCardCreateDTO;
import com.quest.dto.rest.SpecialCard.SpecialCardResponseDTO;
import com.quest.dto.rest.SpecialCard.SpecialCardUpdateDTO;
import com.quest.services.rest.SpecialCardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/specialCards")
@Tag(name = "SpecialCard", description = "Special Card management")
public class SpecialCardController {
    private final SpecialCardService specialCardService;

    @Autowired
    public SpecialCardController(SpecialCardService specialCardService) {
        this.specialCardService = specialCardService;
    }

    @PostMapping
    @Operation(summary = "Create a new special card")
    public ResponseEntity<SpecialCardResponseDTO> createSpecialCard(
            @RequestBody SpecialCardCreateDTO specialCardCreateDTO) {
        SpecialCardResponseDTO createdSpecialCard = specialCardService.create(specialCardCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpecialCard);
    }

    @GetMapping
    @Operation(summary = "Get all special cards")
    public ResponseEntity<List<SpecialCardResponseDTO>> findAllSpecialCards() {
        List<SpecialCardResponseDTO> specialCards = specialCardService.findAll();
        return ResponseEntity.ok(specialCards);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a special card by ID")
    public ResponseEntity<SpecialCardResponseDTO> findSpecialCardById(@PathVariable long id) {
        SpecialCardResponseDTO specialCard = specialCardService.findById(id);
        return ResponseEntity.ok(specialCard);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing special card")
    public ResponseEntity<SpecialCardResponseDTO> updateSpecialCard(@PathVariable long id,
            @Valid @RequestBody SpecialCardUpdateDTO specialCardUpdateDTO) {
        specialCardUpdateDTO.setId(id);
        SpecialCardResponseDTO updatedSpecialCard = specialCardService.update(specialCardUpdateDTO);
        return ResponseEntity.ok(updatedSpecialCard);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a special card by ID")
    public ResponseEntity<Void> deleteSpecialCard(@PathVariable long id) {
        specialCardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

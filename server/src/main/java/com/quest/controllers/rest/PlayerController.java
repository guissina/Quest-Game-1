package com.quest.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quest.dto.rest.Player.PlayerBalanceDTO;
import com.quest.dto.rest.Player.PlayerCreateDTO;
import com.quest.dto.rest.Player.PlayerResponseDTO;
import com.quest.dto.rest.Player.PlayerThemesDTO;
import com.quest.dto.rest.Player.PlayerUpdateDTO;
import com.quest.services.rest.PlayerServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/players")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Player", description = "Player management")
public class PlayerController {
    private final PlayerServices playerServices;

    @Autowired
    public PlayerController(PlayerServices playerServices) {
        this.playerServices = playerServices;
    }

    @PostMapping
    @Operation(summary = "Create a new player")
    public ResponseEntity<PlayerResponseDTO> createPlayer(@RequestBody @Valid PlayerCreateDTO playerCreateDTO) {
        PlayerResponseDTO createdPlayer = playerServices.create(playerCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing player")
    public ResponseEntity<PlayerResponseDTO> updatePlayer(@PathVariable long id,
            @Valid @RequestBody PlayerUpdateDTO playerUpdateDTO) {

        playerUpdateDTO.setId(id);
        PlayerResponseDTO updatedPlayer = playerServices.update(playerUpdateDTO);
        return ResponseEntity.ok(updatedPlayer);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a player by ID")
    public ResponseEntity<PlayerResponseDTO> getPlayerById(@PathVariable long id) {
        PlayerResponseDTO player = playerServices.findById(id);
        return ResponseEntity.ok(player);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get a player by email")
    public ResponseEntity<PlayerResponseDTO> getPlayerByEmail(@PathVariable("email") String email) {
        PlayerResponseDTO player = playerServices.findByEmail(email);
        return ResponseEntity.ok(player);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get a player by name")
    public ResponseEntity<PlayerResponseDTO> getPlayersByName(@PathVariable("name") String name) {
        PlayerResponseDTO player = playerServices.findByName(name);
        return ResponseEntity.ok(player);
    }

    @PatchMapping("/{id}/addTheme")
    @Operation(summary = "Add a theme to a player")
    public ResponseEntity<Void> addTheme(@PathVariable Long id, @Valid @RequestBody PlayerThemesDTO playerThemesDTO) {
        playerServices.addTheme(id, playerThemesDTO.getThemeId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/addBalance")
    @Operation(summary = "Add balance to a player, when buying coins")
    public ResponseEntity<Void> addBalance(@PathVariable long id, @RequestBody PlayerBalanceDTO playerBalanceDTO) {
        playerServices.addBalance(id, playerBalanceDTO.getBalance());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/decreaseBalance")
    @Operation(summary = "Decrease balance of a player, when purchasing an item")
    public ResponseEntity<Void> decreaseBalance(@PathVariable long id, @RequestBody PlayerBalanceDTO playerBalanceDTO) {
        playerServices.decreaseBalance(id, playerBalanceDTO.getBalance());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get all players")
    public ResponseEntity<List<PlayerResponseDTO>> getAllPlayers() {
        List<PlayerResponseDTO> players = playerServices.findAll();
        return ResponseEntity.ok(players);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a player by ID")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerServices.deletePlayerById(id);
        return ResponseEntity.noContent().build();
    }

}

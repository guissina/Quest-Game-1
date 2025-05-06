package com.Quest.quest.controllers;

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

import com.Quest.quest.dto.Player.PlayerCreateDTO;
import com.Quest.quest.dto.Player.PlayerResponseDTO;
import com.Quest.quest.dto.Player.PlayerUpdateDTO;
import com.Quest.quest.services.PlayerServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerServices playerServices;

    @Autowired
    public PlayerController(PlayerServices playerServices) {
        this.playerServices = playerServices;
    }

    @PostMapping
    public ResponseEntity<PlayerResponseDTO> createPlayer(@RequestBody PlayerCreateDTO playerCreateDTO) {
        PlayerResponseDTO createdPlayer = playerServices.create(playerCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> updatePlayer(@PathVariable long id,
            @Valid @RequestBody PlayerUpdateDTO playerUpdateDTO) {

        playerUpdateDTO.setId(id);
        PlayerResponseDTO updatedPlayer = playerServices.update(playerUpdateDTO);
        return ResponseEntity.ok(updatedPlayer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> getPlayerById(@PathVariable long id) {
        PlayerResponseDTO player = playerServices.findById(id);
        return ResponseEntity.ok(player);
    }

    @GetMapping("/email")
    public ResponseEntity<PlayerResponseDTO> getPlayerByEmail(@PathVariable String email) {
        PlayerResponseDTO player = playerServices.findByEmail(email);
        return ResponseEntity.ok(player);
    }

    @GetMapping
    public ResponseEntity<List<PlayerResponseDTO>> getAllPlayers() {
        List<PlayerResponseDTO> players = playerServices.findAll();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/findByName")
    public ResponseEntity<List<PlayerResponseDTO>> getPlayersByName(@RequestBody PlayerUpdateDTO playerUpdateDTO) {
        List<PlayerResponseDTO> players = playerServices.findAll();
        return ResponseEntity.ok(players);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerServices.deletePlayerById(id);
        return ResponseEntity.noContent().build();
    }

}

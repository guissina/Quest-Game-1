package com.Quest.quest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Quest.quest.dto.Player.PlayerCreateDTO;
import com.Quest.quest.dto.Player.PlayerResponseDTO;
import com.Quest.quest.services.PlayerServices;

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

}

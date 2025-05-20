package com.quest.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quest.dto.rest.Player.PlayerLoginDTO;
import com.quest.dto.rest.Player.PlayerResponseDTO;
import com.quest.interfaces.Auth.IAuthServices;
import com.quest.mappers.PlayerMapper;
import com.quest.repositories.PlayerRepository;

@Service
public class AuthService implements IAuthServices {
    private final PlayerMapper playerMapper;
    private final PlayerRepository playerRepository;

    @Autowired
    public AuthService(PlayerMapper playerMapper, PlayerRepository playerRepository) {
        this.playerMapper = playerMapper;
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerResponseDTO authenticate(PlayerLoginDTO playerLoginDTO) {
        return playerRepository.findByEmail(playerLoginDTO.getEmail())
                .filter(player -> player.getPassword().equals(playerLoginDTO.getPassword()))
                .map(playerMapper::toPlayerResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    }
}

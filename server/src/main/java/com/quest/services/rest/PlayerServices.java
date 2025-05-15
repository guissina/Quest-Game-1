package com.quest.services.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quest.dto.rest.Player.PlayerCreateDTO;
import com.quest.dto.rest.Player.PlayerResponseDTO;
import com.quest.dto.rest.Player.PlayerUpdateDTO;
import com.quest.interfaces.rest.IPlayerServices;
import com.quest.mappers.PlayerMapper;
import com.quest.models.Player;
import com.quest.repositories.PlayerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PlayerServices implements IPlayerServices {

    private final PlayerMapper playerMapper;
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServices(PlayerMapper playerMapper, PlayerRepository playerRepository) {
        this.playerMapper = playerMapper;
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerResponseDTO create(PlayerCreateDTO playerCreateDTO) {
        Player player = playerMapper.toEntity(playerCreateDTO);
        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toPlayerResponseDTO(savedPlayer);
    }

    @Override
    public Player findPlayerById(long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));
    }

    @Override
    public PlayerResponseDTO findById(long id) {
        Player player = findPlayerById(id);
        return playerMapper.toPlayerResponseDTO(player);
    }

    @Override
    public List<PlayerResponseDTO> findAll() {
        List<Player> players = playerRepository.findAll();
        return playerMapper.toPlayerResponseDTOs(players);
    }

    @Override
    public PlayerResponseDTO update(PlayerUpdateDTO playerUpdateDTO) {
        Player currentPlayer = findPlayerById(playerUpdateDTO.getId());

        if (!currentPlayer.getEmail().equals(playerUpdateDTO.getEmail()))
            currentPlayer.setEmail(playerUpdateDTO.getEmail());

        if (!currentPlayer.getName().equals(playerUpdateDTO.getName()))
            currentPlayer.setName(playerUpdateDTO.getName());

        Player player = playerMapper.toEntity(playerUpdateDTO);

        Player updatedPlayer = playerRepository.save(player);
        return playerMapper.toPlayerResponseDTO(updatedPlayer);
    }

    @Override
    public PlayerResponseDTO findByName(String name) {
        Player player = playerRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with name: " + name));
        return playerMapper.toPlayerResponseDTO(player);
    }

    @Override
    public PlayerResponseDTO findByEmail(String email) {
        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with email: " + email));
        return playerMapper.toPlayerResponseDTO(player);
    }

    @Override
    public void deletePlayerById(long id) {
        Player player = findPlayerById(id);
        playerRepository.delete(player);
    }
}

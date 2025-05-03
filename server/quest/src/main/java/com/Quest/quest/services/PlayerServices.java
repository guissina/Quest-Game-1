package com.Quest.quest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Quest.quest.dto.Player.PlayerResponseDTO;
import com.Quest.quest.dto.Player.PlayerUpdateDTO;
import com.Quest.quest.interfaces.IPlayerServices;
import com.Quest.quest.mappers.PlayerMapper;
import com.Quest.quest.models.Player;
import com.Quest.quest.repositories.PlayerRepository;

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
    public PlayerResponseDTO create(PlayerUpdateDTO playerCreateDTO) {
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
    public List<PlayerResponseDTO> findAll() {
        List<Player> players = playerRepository.findAll();
        return playerMapper.toPlayerResponseDTOs(players);
    }

    @Override
    public PlayerResponseDTO findById(long id) {
        Player player = findPlayerById(id);
        return playerMapper.toPlayerResponseDTO(player);
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

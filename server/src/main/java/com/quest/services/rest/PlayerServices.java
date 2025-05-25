package com.quest.services.rest;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quest.dto.rest.Player.PlayerCreateDTO;
import com.quest.dto.rest.Player.PlayerResponseDTO;
import com.quest.dto.rest.Player.PlayerUpdateDTO;
import com.quest.interfaces.rest.IPlayerServices;
import com.quest.mappers.PlayerMapper;
import com.quest.models.Player;
import com.quest.models.Theme;
import com.quest.repositories.PlayerRepository;
import com.quest.repositories.ThemeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PlayerServices implements IPlayerServices {

    private final PlayerMapper playerMapper;
    private final PlayerRepository playerRepository;
    private final ThemeRepository themeRepository;

    @Autowired
    public PlayerServices(PlayerMapper playerMapper, PlayerRepository playerRepository,
            ThemeRepository themeRepository) {
        this.playerMapper = playerMapper;
        this.playerRepository = playerRepository;
        this.themeRepository = themeRepository;
    }

    @Override
    public PlayerResponseDTO create(PlayerCreateDTO playerCreateDTO) {
        if (existsByEmail(playerCreateDTO.getEmail()))
            throw new IllegalArgumentException("Email already exists");

        if (existsByName(playerCreateDTO.getName()))
            throw new IllegalArgumentException("Name already exists");

        Player player = playerMapper.toEntity(playerCreateDTO);

        List<Theme> freeThemes = themeRepository.findAllByFreeTrue();

        player.setThemes(freeThemes);

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

        if (!currentPlayer.getEmail().equals(playerUpdateDTO.getEmail())) {
            if (playerRepository.existsByEmailAndIdNot(playerUpdateDTO.getEmail(), currentPlayer.getId())) {
                throw new IllegalArgumentException("Email already exists");
            }
            currentPlayer.setEmail(playerUpdateDTO.getEmail());
        }

        if (!currentPlayer.getName().equals(playerUpdateDTO.getName())) {
            if (playerRepository.existsByNameAndIdNot(playerUpdateDTO.getName(), currentPlayer.getId())) {
                throw new IllegalArgumentException("Name already exists");
            }
            currentPlayer.setName(playerUpdateDTO.getName());
        }

        Player updatedPlayer = playerRepository.save(currentPlayer);
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

    @Override
    public void addBalance(long id, BigDecimal balance) {
        Player player = findPlayerById(id);
        player.setBalance(player.getBalance().add(balance));
        playerRepository.save(player);
    }

    @Override
    public void decreaseBalance(long id, BigDecimal balance) {
        Player player = findPlayerById(id);
        player.setBalance(player.getBalance().subtract(balance));
        if (player.getBalance().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Insufficient balance");

        playerRepository.save(player);
    }

    @Override
    public void addTheme(long playerId, long themeId) {
        Player player = findPlayerById(playerId);
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found with id: " + themeId));

        if (player.getThemes().contains(theme)) {
            throw new IllegalArgumentException("Theme already exists for this player");
        }

        player.getThemes().add(theme);
        playerRepository.save(player);
    }

    private Boolean existsByEmail(String email) {
        return playerRepository.existsByEmail(email);
    }

    private Boolean existsByName(String name) {
        return playerRepository.existsByName(name);
    }
}

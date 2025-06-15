package com.quest.services.rest;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quest.dto.rest.Player.PlayerCreateDTO;
import com.quest.dto.rest.Player.PlayerResponseDTO;
import com.quest.dto.rest.Player.PlayerUpdateDTO;
import com.quest.dto.rest.Theme.ThemeResponseDTO;
import com.quest.interfaces.rest.IPlayerServices;
import com.quest.mappers.PlayerMapper;
import com.quest.mappers.ThemeMapper;
import com.quest.models.Player;
import com.quest.models.PlayerTheme;
import com.quest.models.Theme;
import com.quest.repositories.PlayerRepository;
import com.quest.repositories.PlayerThemeRepository;
import com.quest.repositories.ThemeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PlayerServices implements IPlayerServices {

    private final PlayerMapper playerMapper;
    private final PlayerRepository playerRepository;
    private final ThemeRepository themeRepository;
    private final PlayerThemeRepository playerThemeRepository;
    private final ThemeMapper themeMapper;

    @Autowired
    public PlayerServices(PlayerMapper playerMapper, PlayerRepository playerRepository,
            ThemeRepository themeRepository, PlayerThemeRepository playerThemeRepository, ThemeMapper themeMapper) {
        this.playerThemeRepository = playerThemeRepository;
        this.playerMapper = playerMapper;
        this.playerRepository = playerRepository;
        this.themeRepository = themeRepository;
        this.themeMapper = themeMapper;
    }

    @Override
    public long count() {
        return playerRepository.count();
    }

    @Override
    public PlayerResponseDTO create(PlayerCreateDTO playerCreateDTO) {
        if (existsByEmail(playerCreateDTO.getEmail()))
            throw new IllegalArgumentException("Email already exists");

        if (existsByName(playerCreateDTO.getName()))
            throw new IllegalArgumentException("Name already exists");

        Player player = playerMapper.toEntity(playerCreateDTO);

        List<Theme> freeThemes = themeRepository.findAllByFreeTrue();
        List<PlayerTheme> playerThemes = freeThemes.stream()
                .map(theme -> {
                    PlayerTheme pt = new PlayerTheme();
                    pt.setPlayer(player);
                    pt.setTheme(theme);
                    return pt;
                })
                .toList();

        player.setPlayerThemes(playerThemes);

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

        if (!currentPlayer.getAvatarIndex().equals(playerUpdateDTO.getAvatarIndex())) {
            currentPlayer.setAvatarIndex(playerUpdateDTO.getAvatarIndex());
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
    public List<ThemeResponseDTO> findPlayerThemes(Long id) {
        Player player = findPlayerById(id);
        return player.getPlayerThemes().stream()
                .map(PlayerTheme::getTheme)
                .map(themeMapper::toThemeResponseDTO)
                .toList();
    }

    @Override
    public PlayerResponseDTO addBalance(long id, BigDecimal balance) {
        Player player = findPlayerById(id);
        if (balance.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Balance cannot be negative");
        player.setBalance(player.getBalance().add(balance));
        playerRepository.save(player);
        return playerMapper.toPlayerResponseDTO(player);
    }

    @Override
    public PlayerResponseDTO decreaseBalance(long id, BigDecimal balance) {
        Player player = findPlayerById(id);
        if (balance.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Balance cannot be negative");

        player.setBalance(player.getBalance().subtract(balance));
        if (player.getBalance().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Insufficient balance");

        playerRepository.save(player);
        return playerMapper.toPlayerResponseDTO(player);
    }

    // Todo: Implement verification of theme ownership
    @Transactional
    @Override
    public void addTheme(Long playerId, Long themeId, BigDecimal balance) {
        decreaseBalance(playerId, balance);

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + playerId));
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found with id: " + themeId));

        if (playerThemeRepository.existsByPlayerIdAndThemeId(playerId, themeId))
            throw new IllegalArgumentException("Player already owns this theme");

        PlayerTheme pt = new PlayerTheme();
        pt.setPlayer(player);
        pt.setTheme(theme);
        playerThemeRepository.save(pt);
    }

    private Boolean existsByEmail(String email) {
        return playerRepository.existsByEmail(email);
    }

    private Boolean existsByName(String name) {
        return playerRepository.existsByName(name);
    }
}

package com.quest.interfaces.rest;

import java.util.List;

import com.quest.dto.rest.Player.PlayerCreateDTO;
import com.quest.dto.rest.Player.PlayerResponseDTO;
import com.quest.dto.rest.Player.PlayerUpdateDTO;
import com.quest.models.Player;

public interface IPlayerServices {

    Player findPlayerById(long id);

    PlayerResponseDTO create(PlayerCreateDTO playerCreateDTO);

    PlayerResponseDTO findById(long id);

    void deletePlayerById(long id);

    PlayerResponseDTO update(PlayerUpdateDTO playerUpdateDTO);

    PlayerResponseDTO findByName(String name);

    PlayerResponseDTO findByEmail(String email);

    List<PlayerResponseDTO> findAll();
}

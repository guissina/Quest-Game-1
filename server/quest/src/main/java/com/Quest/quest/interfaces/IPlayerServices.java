package com.Quest.quest.interfaces;

import java.util.List;

import com.Quest.quest.dto.Player.PlayerCreateDTO;
import com.Quest.quest.dto.Player.PlayerResponseDTO;
import com.Quest.quest.dto.Player.PlayerUpdateDTO;
import com.Quest.quest.models.Player;

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

package com.Quest.quest.interfaces;

import com.Quest.quest.dto.Player.PlayerResponseDTO;
import com.Quest.quest.dto.Player.PlayerUpdateDTO;
import com.Quest.quest.models.Player;

public interface IPlayerServices {

    Player findPlayerById(long id);

    PlayerResponseDTO findById(long id);

    void deletePlayerById(long id);

    PlayerResponseDTO update(PlayerUpdateDTO playerUpdateDTO);
}

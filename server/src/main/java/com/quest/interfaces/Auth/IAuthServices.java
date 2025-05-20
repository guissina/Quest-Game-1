package com.quest.interfaces.Auth;

import com.quest.dto.rest.Player.PlayerLoginDTO;
import com.quest.dto.rest.Player.PlayerResponseDTO;

public interface IAuthServices {
    PlayerResponseDTO authenticate(PlayerLoginDTO playerLoginDTO);
}

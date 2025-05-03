package com.Quest.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Quest.quest.dto.Player.PlayerResponseDTO;
import com.Quest.quest.dto.Player.PlayerUpdateDTO;
import com.Quest.quest.models.Player;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerResponseDTO toPlayerResponseDTO(Player player);

    Player toEntity(PlayerResponseDTO playerResponseDTO);

    Player toEntity(PlayerUpdateDTO playerUpdateDTO);

    List<PlayerResponseDTO> toPlayerResponseDTOs(List<Player> players);
}

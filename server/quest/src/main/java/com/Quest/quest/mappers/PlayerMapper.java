package com.Quest.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.Quest.quest.dto.Player.PlayerCreateDTO;
import com.Quest.quest.dto.Player.PlayerResponseDTO;
import com.Quest.quest.dto.Player.PlayerUpdateDTO;
import com.Quest.quest.models.Player;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerResponseDTO toPlayerResponseDTO(Player player);

    Player toEntity(PlayerResponseDTO playerResponseDTO);

    @Mapping(target = "id", ignore = true)
    Player toEntity(PlayerCreateDTO playerCreateDTO);

    @Mapping(target = "password", source = "playerUpdateDTO.id")
    Player toEntity(PlayerUpdateDTO playerUpdateDTO);

    List<PlayerResponseDTO> toPlayerResponseDTOs(List<Player> players);
}

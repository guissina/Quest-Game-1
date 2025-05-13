package com.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.quest.dto.rest.Player.PlayerCreateDTO;
import com.quest.dto.rest.Player.PlayerResponseDTO;
import com.quest.dto.rest.Player.PlayerUpdateDTO;
import com.quest.models.Player;

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

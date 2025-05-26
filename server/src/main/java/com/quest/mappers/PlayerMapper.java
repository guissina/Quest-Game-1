package com.quest.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.quest.dto.rest.Player.PlayerBalanceDTO;
import com.quest.dto.rest.Player.PlayerCreateDTO;
import com.quest.dto.rest.Player.PlayerLoginDTO;
import com.quest.dto.rest.Player.PlayerResponseDTO;
import com.quest.dto.rest.Player.PlayerUpdateDTO;
import com.quest.dto.ws.Room.PlayerRoomResponseDTO;
import com.quest.models.Player;
import com.quest.models.PlayerTheme;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(target = "themeIds", expression = "java(mapPlayerThemesToThemeIds(player.getPlayerThemes()))")
    PlayerResponseDTO toPlayerResponseDTO(Player player);

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "playerThemes", ignore = true)
    Player toEntity(PlayerCreateDTO playerCreateDTO);

    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "playerThemes", ignore = true)
    Player toEntity(PlayerUpdateDTO playerUpdateDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    PlayerRoomResponseDTO toPlayerRoomResponseDTO(Player player);

    PlayerBalanceDTO toPlayerBalanceDTO(Player player);

    PlayerLoginDTO toPlayerLoginDTO(Player player);

    List<PlayerRoomResponseDTO> toPlayerRoomResponseDTOs(List<Player> players);

    List<PlayerResponseDTO> toPlayerResponseDTOs(List<Player> players);

    default List<Long> mapPlayerThemesToThemeIds(List<PlayerTheme> playerThemes) {
        if (playerThemes == null)
            return null;
        return playerThemes.stream()
                .map(playerTheme -> playerTheme.getTheme().getId())
                .collect(Collectors.toList());
    }

}

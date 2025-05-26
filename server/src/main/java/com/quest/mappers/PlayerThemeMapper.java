package com.quest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.quest.dto.rest.PlayerTheme.PlayerThemeCreateDTO;
import com.quest.dto.rest.PlayerTheme.PlayerThemeResponseDTO;
import com.quest.models.PlayerTheme;

@Mapper(componentModel = "spring")
public interface PlayerThemeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "player.id", source = "playerId")
    @Mapping(target = "theme.id", source = "themeId")
    PlayerTheme toEntity(PlayerThemeCreateDTO dto);

    @Mapping(target = "playerId", source = "player.id")
    @Mapping(target = "themeId", source = "theme.id")
    PlayerThemeResponseDTO toDTO(PlayerTheme entity);
}

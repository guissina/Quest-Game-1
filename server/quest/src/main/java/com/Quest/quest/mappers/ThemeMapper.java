package com.Quest.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.Quest.quest.dto.Theme.ThemeCreateDTO;
import com.Quest.quest.dto.Theme.ThemeResponseDTO;
import com.Quest.quest.models.Theme;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    ThemeResponseDTO toThemeResponseDTO(Theme theme);

    Theme toEntity(ThemeResponseDTO themeResponseDTO);

    @Mapping(target = "id", ignore = true)
    Theme toEntity(ThemeCreateDTO themeCreateDTO);

    List<ThemeResponseDTO> toThemeResponseDTOs(List<Theme> themes);
}

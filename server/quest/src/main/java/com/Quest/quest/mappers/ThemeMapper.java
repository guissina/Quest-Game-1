package com.Quest.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Quest.quest.dto.Theme.ThemeCreateDTO;
import com.Quest.quest.dto.Theme.ThemeResponseDTO;
import com.Quest.quest.models.Theme;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    ThemeResponseDTO toThemeResponseDTO(Theme theme);

    Theme toEntity(ThemeResponseDTO themeResponseDTO);

    Theme toEntity(ThemeCreateDTO themeCreateDTO);

    List<ThemeResponseDTO> toThemeResponseDTOs(List<Theme> themes);
}

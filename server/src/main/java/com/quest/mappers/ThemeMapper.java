package com.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.quest.dto.rest.Theme.ThemeCreateDTO;
import com.quest.dto.rest.Theme.ThemeResponseDTO;
import com.quest.models.Theme;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    ThemeResponseDTO toThemeResponseDTO(Theme theme);

    Theme toEntity(ThemeResponseDTO themeResponseDTO);

    @Mapping(target = "id", ignore = true)
    Theme toEntity(ThemeCreateDTO themeCreateDTO);

    List<ThemeResponseDTO> toThemeResponseDTOs(List<Theme> themes);
}

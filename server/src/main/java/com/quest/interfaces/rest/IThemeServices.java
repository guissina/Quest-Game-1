package com.quest.interfaces.rest;

import java.util.List;

import com.quest.dto.rest.Theme.ThemeCreateDTO;
import com.quest.dto.rest.Theme.ThemeResponseDTO;
import com.quest.dto.rest.Theme.ThemeUpdateDTO;
import com.quest.models.Theme;

public interface IThemeServices {
    Theme findThemeById(long id);

    List<Theme> findThemesByIds(List<Long> ids);

    ThemeResponseDTO findByName(String name);

    ThemeResponseDTO findByCode(String code);

    ThemeResponseDTO create(ThemeCreateDTO themeCreateDTO);

    List<ThemeResponseDTO> createMany(List<ThemeCreateDTO> themeCreateDTOs);

    List<ThemeResponseDTO> findAll();

    ThemeResponseDTO findById(long id);

    ThemeResponseDTO update(ThemeUpdateDTO themeUpdateDTO);

    ThemeResponseDTO updateThemeAvailability(long id, Boolean isFree);

    void delete(long id);
}

package com.quest.interfaces.rest;

import java.util.List;

import com.quest.dto.rest.Theme.ThemeCreateDTO;
import com.quest.dto.rest.Theme.ThemeResponseDTO;
import com.quest.dto.rest.Theme.ThemeUpdateDTO;
import com.quest.models.Theme;

public interface IThemeServices {
    Theme findThemeById(long id);

    ThemeResponseDTO create(ThemeCreateDTO themeCreateDTO);

    List<ThemeResponseDTO> findAll();

    ThemeResponseDTO findById(long id);

    ThemeResponseDTO update(ThemeUpdateDTO themeUpdateDTO);

    void delete(long id);
}

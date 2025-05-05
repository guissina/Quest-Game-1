package com.Quest.quest.interfaces;

import java.util.List;

import com.Quest.quest.dto.Theme.ThemeCreateDTO;
import com.Quest.quest.dto.Theme.ThemeResponseDTO;
import com.Quest.quest.dto.Theme.ThemeUpdateDTO;
import com.Quest.quest.models.Theme;

public interface IThemeServices {
    Theme findThemeById(long id);

    ThemeResponseDTO create(ThemeCreateDTO themeCreateDTO);

    List<ThemeResponseDTO> findAll();

    ThemeResponseDTO findById(long id);

    ThemeResponseDTO update(ThemeUpdateDTO themeUpdateDTO);

    void delete(long id);
}

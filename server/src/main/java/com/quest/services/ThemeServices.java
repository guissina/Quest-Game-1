package com.quest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quest.dto.Theme.ThemeCreateDTO;
import com.quest.dto.Theme.ThemeResponseDTO;
import com.quest.dto.Theme.ThemeUpdateDTO;
import com.quest.interfaces.IThemeServices;
import com.quest.mappers.ThemeMapper;
import com.quest.models.Theme;
import com.quest.repositories.ThemeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ThemeServices implements IThemeServices {

    private final ThemeMapper themeMapper;
    private final ThemeRepository themeRepository;

    @Autowired
    public ThemeServices(ThemeMapper themeMapper, ThemeRepository themeRepository) {
        this.themeMapper = themeMapper;
        this.themeRepository = themeRepository;
    }

    @Override
    public ThemeResponseDTO create(ThemeCreateDTO themeCreateDTO) {
        Theme theme = themeMapper.toEntity(themeCreateDTO);
        Theme savedTheme = themeRepository.save(theme);
        return themeMapper.toThemeResponseDTO(savedTheme);
    }

    @Override
    public List<ThemeResponseDTO> findAll() {
        List<Theme> themes = themeRepository.findAll();
        return themeMapper.toThemeResponseDTOs(themes);
    }

    @Override
    public Theme findThemeById(long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found with id: " + id));
    }

    @Override
    public ThemeResponseDTO findById(long id) {
        Theme theme = findThemeById(id);
        return themeMapper.toThemeResponseDTO(theme);
    }

    @Override
    public ThemeResponseDTO update(ThemeUpdateDTO themeUpdateDTO) {
        Theme currentTheme = findThemeById(themeUpdateDTO.getId());

        if (!currentTheme.getName().equals(themeUpdateDTO.getName()))
            currentTheme.setName(themeUpdateDTO.getName());

        if (!currentTheme.getName().equals(themeUpdateDTO.getName()))
            currentTheme.setName(themeUpdateDTO.getName());

        Theme updatedTheme = themeRepository.save(currentTheme);
        return themeMapper.toThemeResponseDTO(updatedTheme);
    }

    @Override
    public void delete(long id) {
        Theme theme = findThemeById(id);
        themeRepository.delete(theme);
    }

}

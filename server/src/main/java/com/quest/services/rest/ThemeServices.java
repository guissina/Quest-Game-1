package com.quest.services.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quest.dto.rest.Theme.ThemeCreateDTO;
import com.quest.dto.rest.Theme.ThemeResponseDTO;
import com.quest.dto.rest.Theme.ThemeUpdateDTO;
import com.quest.interfaces.rest.IThemeServices;
import com.quest.mappers.ThemeMapper;
import com.quest.models.Theme;
import com.quest.repositories.ThemeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

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
    @Transactional
    public List<ThemeResponseDTO> createMany(List<@Valid ThemeCreateDTO> themeCreateDTOList) {
        // Converte cada DTO em entidade
        List<Theme> themesToSave = themeCreateDTOList.stream()
                .map(dto -> themeMapper.toEntity(dto))
                .collect(Collectors.toList());

        // Persiste todos em batch
        List<Theme> savedThemes = themeRepository.saveAll(themesToSave);

        // Mapeia entidades salvas para DTOs de resposta
        return savedThemes.stream()
                .map(themeMapper::toThemeResponseDTO)
                .collect(Collectors.toList());
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
    public ThemeResponseDTO findByName(String name) {
        Theme theme = themeRepository.findByName(name);
        if (theme == null) {
            throw new EntityNotFoundException("Theme not found with name: " + name);
        }
        return themeMapper.toThemeResponseDTO(theme);
    }

    @Override
    public ThemeResponseDTO findByCode(String code) {
        Theme theme = themeRepository.findByCode(code);
        if (theme == null) {
            throw new EntityNotFoundException("Theme not found with code: " + code);
        }
        return themeMapper.toThemeResponseDTO(theme);
    }

    @Override
    public ThemeResponseDTO update(ThemeUpdateDTO themeUpdateDTO) {
        Theme currentTheme = findThemeById(themeUpdateDTO.getId());

        if (!currentTheme.getName().equals(themeUpdateDTO.getName()))
            currentTheme.setName(themeUpdateDTO.getName());

        if (!currentTheme.getCode().equals(themeUpdateDTO.getCode()))
            currentTheme.setCode(themeUpdateDTO.getCode());

        currentTheme.setFree(themeUpdateDTO.isFree());

        Theme updatedTheme = themeRepository.save(currentTheme);
        return themeMapper.toThemeResponseDTO(updatedTheme);
    }

    @Override
    public ThemeResponseDTO updateThemeAvailability(long id, Boolean isFree) {
        Theme currentTheme = findThemeById(id);
        currentTheme.setFree(isFree);
        Theme updatedTheme = themeRepository.save(currentTheme);
        return themeMapper.toThemeResponseDTO(updatedTheme);
    }

    @Override
    public void delete(long id) {
        Theme theme = findThemeById(id);
        themeRepository.delete(theme);
    }

}

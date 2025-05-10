package com.quest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quest.dto.SpecialCard.SpecialCardCreateDTO;
import com.quest.dto.SpecialCard.SpecialCardResponseDTO;
import com.quest.dto.SpecialCard.SpecialCardUpdateDTO;
import com.quest.interfaces.ISpecialCardServices;
import com.quest.mappers.SpecialCardMapper;
import com.quest.models.SpecialCard;
import com.quest.repositories.SpecialCardRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SpecialCardService implements ISpecialCardServices {

    private final SpecialCardRepository specialCardRepository;
    private final SpecialCardMapper specialCardMapper;

    @Autowired
    public SpecialCardService(SpecialCardRepository specialCardRepository, SpecialCardMapper specialCardMapper) {
        this.specialCardRepository = specialCardRepository;
        this.specialCardMapper = specialCardMapper;
    }

    @Override
    public SpecialCardResponseDTO create(SpecialCardCreateDTO specialCardCreateDTO) {
        SpecialCard specialCard = specialCardMapper.toEntity(specialCardCreateDTO);
        SpecialCard savedSpecialCard = specialCardRepository.save(specialCard);
        return specialCardMapper.toSpecialCardResponseDTO(savedSpecialCard);
    }

    @Override
    public SpecialCard findSpecialCardById(long id) {
        return specialCardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SpecialCard not found with id: " + id));
    }

    @Override
    public List<SpecialCardResponseDTO> findAll() {
        List<SpecialCard> specialCards = specialCardRepository.findAll();
        return specialCardMapper.toSpecialCardResponseDTOs(specialCards);
    }

    @Override
    public SpecialCardResponseDTO update(SpecialCardUpdateDTO specialCardUpdateDTO) {
        SpecialCard specialCard = specialCardMapper.toEntity(specialCardUpdateDTO);
        SpecialCard updatedSpecialCard = specialCardRepository.save(specialCard);
        return specialCardMapper.toSpecialCardResponseDTO(updatedSpecialCard);
    }

    @Override
    public SpecialCardResponseDTO findById(long id) {
        SpecialCard specialCard = findSpecialCardById(id);
        return specialCardMapper.toSpecialCardResponseDTO(specialCard);
    }

    @Override
    public void delete(long id) {
        SpecialCard specialCard = findSpecialCardById(id);
        specialCardRepository.delete(specialCard);
    }

}

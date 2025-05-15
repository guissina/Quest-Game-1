package com.quest.interfaces.rest;

import java.util.List;

import com.quest.dto.rest.SpecialCard.SpecialCardCreateDTO;
import com.quest.dto.rest.SpecialCard.SpecialCardResponseDTO;
import com.quest.dto.rest.SpecialCard.SpecialCardUpdateDTO;
import com.quest.models.SpecialCard;

public interface ISpecialCardServices {
    void delete(long id);

    SpecialCard findSpecialCardById(long id);

    List<SpecialCardResponseDTO> findAll();

    SpecialCardResponseDTO findById(long id);

    SpecialCardResponseDTO update(SpecialCardUpdateDTO specialCardUpdateDTO);

    SpecialCardResponseDTO create(SpecialCardCreateDTO specialCardCreateDTO);

}

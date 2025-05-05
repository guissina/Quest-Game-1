package com.Quest.quest.interfaces;

import java.util.List;

import com.Quest.quest.dto.SpecialCard.SpecialCardCreateDTO;
import com.Quest.quest.dto.SpecialCard.SpecialCardResponseDTO;
import com.Quest.quest.models.SpecialCard;

public interface ISpecialCardServices {
    void delete(long id);

    SpecialCard findSpecialCardById(long id);

    List<SpecialCardResponseDTO> findAll();

    SpecialCardResponseDTO findById(long id);

    SpecialCardResponseDTO update(SpecialCardCreateDTO specialCardCreateDTO);

    SpecialCardResponseDTO create(SpecialCardCreateDTO specialCardCreateDTO);

}

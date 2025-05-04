package com.Quest.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Quest.quest.dto.SpecialCard.SpecialCardCreateDTO;
import com.Quest.quest.dto.SpecialCard.SpecialCardResponseDTO;
import com.Quest.quest.models.SpecialCard;

@Mapper(componentModel = "spring")
public interface SpecialCardMapper {

    SpecialCardResponseDTO toSpecialCardResponseDTO(SpecialCard specialCard);

    SpecialCard toEntity(SpecialCardResponseDTO specialCardResponseDTO);

    SpecialCard toEntity(SpecialCardCreateDTO specialCardCreateDTO);

    List<SpecialCardResponseDTO> toSpecialCardResponseDTOs(List<SpecialCard> specialCards);

}

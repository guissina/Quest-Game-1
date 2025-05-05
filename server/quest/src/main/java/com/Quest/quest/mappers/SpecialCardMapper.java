package com.Quest.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.Quest.quest.dto.SpecialCard.SpecialCardCreateDTO;
import com.Quest.quest.dto.SpecialCard.SpecialCardResponseDTO;
import com.Quest.quest.models.SpecialCard;

@Mapper(componentModel = "spring")
public interface SpecialCardMapper {

    SpecialCardResponseDTO toSpecialCardResponseDTO(SpecialCard specialCard);

    @Mapping(target = "specialtyType", source = "specialtyType")
    SpecialCard toEntity(SpecialCardResponseDTO specialCardResponseDTO);

    @Mapping(target = "specialtyType", source = "specialtyType")
    @Mapping(target = "id", ignore = true)
    SpecialCard toEntity(SpecialCardCreateDTO specialCardCreateDTO);

    List<SpecialCardResponseDTO> toSpecialCardResponseDTOs(List<SpecialCard> specialCards);

}

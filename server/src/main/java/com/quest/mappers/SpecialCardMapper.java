package com.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.quest.dto.rest.SpecialCard.SpecialCardCreateDTO;
import com.quest.dto.rest.SpecialCard.SpecialCardResponseDTO;
import com.quest.dto.rest.SpecialCard.SpecialCardUpdateDTO;
import com.quest.models.SpecialCard;

@Mapper(componentModel = "spring")
public interface SpecialCardMapper {

    SpecialCardResponseDTO toSpecialCardResponseDTO(SpecialCard specialCard);

    @Mapping(target = "specialtyType", source = "specialtyType")
    SpecialCard toEntity(SpecialCardResponseDTO specialCardResponseDTO);

    @Mapping(target = "specialtyType", source = "specialtyType")
    @Mapping(target = "id", ignore = true)
    SpecialCard toEntity(SpecialCardCreateDTO specialCardCreateDTO);

    @Mapping(target = "specialtyType", source = "specialtyType")
    SpecialCard toEntity(SpecialCardUpdateDTO specialCardUpdateDTO);

    List<SpecialCardResponseDTO> toSpecialCardResponseDTOs(List<SpecialCard> specialCards);

}

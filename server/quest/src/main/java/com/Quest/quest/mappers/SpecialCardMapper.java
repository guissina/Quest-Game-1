package com.Quest.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Quest.quest.dto.SpecialCard.SpecialCardResponse;
import com.Quest.quest.models.SpecialCard;

@Mapper(componentModel = "spring")
public interface SpecialCardMapper {

    SpecialCardResponse toSpecialCardResponseDTO(SpecialCard specialCard);

    SpecialCard toEntity(SpecialCardResponse specialCardResponseDTO);

    List<SpecialCardResponse> toSpecialCardResponseDTOs(List<SpecialCard> specialCards);

}

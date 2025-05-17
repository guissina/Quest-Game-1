package com.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.quest.dto.rest.questionOptions.QuestionOptionCreateDTO;
import com.quest.dto.rest.questionOptions.QuestionOptionResponseDTO;
import com.quest.dto.rest.questionOptions.QuestionOptionUpdateDTO;
import com.quest.models.QuestionOption;

@Mapper(componentModel = "spring")
public interface QuestionOptionsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    QuestionOption toEntity(QuestionOptionCreateDTO dto);

    @Mapping(target = "question", ignore = true)
    QuestionOption toEntity(QuestionOptionUpdateDTO dto);

    QuestionOptionResponseDTO toDto(QuestionOption entity);

    List<QuestionOptionResponseDTO> toDtos(List<QuestionOption> entities);
}

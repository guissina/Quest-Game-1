package com.Quest.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.Quest.quest.dto.Question.QuestionResponseDTO;
import com.Quest.quest.models.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionResponseDTO toQuestionResponseDTO(Question question);

    Question toEntity(QuestionResponseDTO questionResponseDTO);

    List<QuestionResponseDTO> toQuestionResponseDTOs(List<Question> questions);

    @Mapping(target = "id", ignore = true)
    Question toEntityWithoutId(QuestionResponseDTO questionResponseDTO);

    @Mapping(target = "id", ignore = true)
    Question toEntityWithoutId(Question question);
}

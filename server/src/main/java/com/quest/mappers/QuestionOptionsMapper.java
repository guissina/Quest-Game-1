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

    @Mapping(target = "question.id", source = "questionId")
    QuestionOption toQuestionOption(QuestionOptionUpdateDTO questionOptionUpdateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question.id", source = "questionId")
    QuestionOption toQuestionOption(QuestionOptionCreateDTO questionOptionCreateDTO);

    QuestionOptionResponseDTO toQuestionResponseDTO(QuestionOption question);

    List<QuestionOptionResponseDTO> toQuestionOptionResponseDTOs(List<QuestionOption> questionOptions);
}

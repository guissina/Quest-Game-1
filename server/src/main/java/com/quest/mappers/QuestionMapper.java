package com.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.quest.dto.rest.Question.QuestionCreateDTO;
import com.quest.dto.rest.Question.QuestionResponseDTO;
import com.quest.dto.rest.Question.QuestionUpdateDTO;
import com.quest.models.Question;

@Mapper(componentModel = "spring", uses = QuestionOptionsMapper.class, // <— inclusão obrigatória
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface QuestionMapper {

    @Mapping(target = "options", source = "question.options")
    @Mapping(target = "themeId", source = "theme.id")
    QuestionResponseDTO toQuestionResponseDTO(Question question);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "theme.id", source = "themeId")
    Question toEntity(QuestionCreateDTO questionCreateDTO);

    @Mapping(target = "theme.id", source = "themeId")
    @Mapping(target = "id", ignore = true)
    Question toEntity(QuestionUpdateDTO questionUpdateDTO);

    List<QuestionResponseDTO> toQuestionResponseDTOs(List<Question> questions);
}
package com.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.quest.dto.Question.QuestionCreateDTO;
import com.quest.dto.Question.QuestionResponseDTO;
import com.quest.dto.Question.QuestionUpdateDTO;
import com.quest.models.Question;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface QuestionMapper {
    @Mapping(target = "themeId", source = "themes.id")
    QuestionResponseDTO toQuestionResponseDTO(Question question);

    @Mapping(target = "themes.id", source = "themeId")
    Question toEntity(QuestionResponseDTO questionResponseDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "themes.id", source = "themeId")
    Question toEntity(QuestionCreateDTO questionCreateDTO);

    @Mapping(target = "themes.id", source = "themeId")
    Question toEntity(QuestionUpdateDTO questionUpdateDTO);

    List<QuestionResponseDTO> toQuestionResponseDTOs(List<Question> questions);
}
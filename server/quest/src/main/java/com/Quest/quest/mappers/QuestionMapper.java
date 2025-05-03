package com.Quest.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Quest.quest.dto.Question.QuestionCreateDTO;
import com.Quest.quest.dto.Question.QuestionResponseDTO;
import com.Quest.quest.dto.Question.QuestionUpdateDTO;
import com.Quest.quest.models.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionResponseDTO toQuestionResponseDTO(Question question);

    Question toEntity(QuestionResponseDTO questionResponseDTO);

    Question toEntity(QuestionCreateDTO questionCreateDTO);

    Question toEntity(QuestionUpdateDTO questionUpdateDTO);

    List<QuestionResponseDTO> toQuestionResponseDTOs(List<Question> questions);

}

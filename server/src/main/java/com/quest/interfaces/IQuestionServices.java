package com.quest.interfaces;

import java.util.List;

import com.quest.dto.Question.QuestionCreateDTO;
import com.quest.dto.Question.QuestionResponseDTO;
import com.quest.dto.Question.QuestionUpdateDTO;
import com.quest.models.Question;

public interface IQuestionServices {

    Question findQuestionById(long id);

    List<QuestionResponseDTO> findAll();

    QuestionResponseDTO create(QuestionCreateDTO questionCreateDTO);

    QuestionResponseDTO update(QuestionUpdateDTO questionCreateDTO);

    QuestionResponseDTO findById(long id);

    void delete(long id);
}

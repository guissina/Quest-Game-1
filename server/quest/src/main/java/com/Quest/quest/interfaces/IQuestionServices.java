package com.Quest.quest.interfaces;

import java.util.List;

import com.Quest.quest.dto.Question.QuestionCreateDTO;
import com.Quest.quest.dto.Question.QuestionResponseDTO;
import com.Quest.quest.dto.Question.QuestionUpdateDTO;
import com.Quest.quest.models.Question;

public interface IQuestionServices {

    Question findQuestionById(long id);

    List<QuestionResponseDTO> findAll();

    QuestionResponseDTO create(QuestionCreateDTO questionCreateDTO);

    QuestionResponseDTO update(QuestionUpdateDTO questionCreateDTO);

    QuestionResponseDTO findById(long id);

    void delete(long id);
}

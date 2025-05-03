package com.Quest.quest.interfaces;

import com.Quest.quest.dto.Question.QuestionCreateDTO;
import com.Quest.quest.dto.Question.QuestionResponseDTO;
import com.Quest.quest.models.Question;

public interface IQuestionServices {

    Question findQuestionById(long id);

    QuestionResponseDTO create(QuestionCreateDTO questionCreateDTO);

    QuestionResponseDTO findById(long id);
}

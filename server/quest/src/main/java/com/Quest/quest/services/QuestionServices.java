package com.Quest.quest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Quest.quest.dto.Question.QuestionCreateDTO;
import com.Quest.quest.dto.Question.QuestionResponseDTO;
import com.Quest.quest.dto.Question.QuestionUpdateDTO;
import com.Quest.quest.interfaces.IQuestionServices;
import com.Quest.quest.mappers.QuestionMapper;
import com.Quest.quest.models.Question;
import com.Quest.quest.repositories.QuestionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;

@Service
public class QuestionServices implements IQuestionServices {
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServices(QuestionMapper questionMapper, QuestionRepository questionRepository) {
        this.questionMapper = questionMapper;
        this.questionRepository = questionRepository;
    }

    @Override
    public QuestionResponseDTO create(@NotNull QuestionCreateDTO QuestionCreateDTO) {
        Question question = questionMapper.toEntity(QuestionCreateDTO);
        Question savedQuestion = questionRepository.save(question);
        return questionMapper.toQuestionResponseDTO(savedQuestion);
    }

    @Override
    public Question findQuestionById(long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + id));
    }

    @Override
    public QuestionResponseDTO findById(long id) {
        Question question = findQuestionById(id);
        return questionMapper.toQuestionResponseDTO(question);
    }

    @Override
    public List<QuestionResponseDTO> findAll() {
        List<Question> questions = questionRepository.findAll();
        return questionMapper.toQuestionResponseDTOs(questions);
    }

    @Override
    public QuestionResponseDTO update(@NotNull QuestionUpdateDTO questionUpdateDTO) {
        Question currentQuestion = findQuestionById(questionUpdateDTO.getId());

        if (!currentQuestion.getQuestionText().equals(questionUpdateDTO.getQuestionText()))
            currentQuestion.setQuestionText(questionUpdateDTO.getQuestionText());
        if (!currentQuestion.getAnswer().equals(questionUpdateDTO.getAnswer()))
            currentQuestion.setAnswer(questionUpdateDTO.getAnswer());
        if (!currentQuestion.getDifficulty().equals(questionUpdateDTO.getDifficulty()))
            currentQuestion.setDifficulty(questionUpdateDTO.getDifficulty());

        currentQuestion = questionRepository.save(currentQuestion);
        Question updatedQuestion = questionRepository.save(currentQuestion);
        return questionMapper.toQuestionResponseDTO(updatedQuestion);
    }

    @Override
    public void delete(long id) {
        Question question = findQuestionById(id);
        questionRepository.delete(question);
    }
}

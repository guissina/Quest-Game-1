package com.quest.services.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quest.dto.rest.Question.QuestionCreateDTO;
import com.quest.dto.rest.Question.QuestionResponseDTO;
import com.quest.dto.rest.Question.QuestionUpdateDTO;
import com.quest.interfaces.IQuestionServices;
import com.quest.mappers.QuestionMapper;
import com.quest.models.Question;
import com.quest.models.Theme;
import com.quest.repositories.QuestionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;

@Service
public class QuestionServices implements IQuestionServices {
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final ThemeServices themeServices;

    @Autowired
    public QuestionServices(QuestionMapper questionMapper, QuestionRepository questionRepository,
            ThemeServices themeServices) {
        this.questionMapper = questionMapper;
        this.questionRepository = questionRepository;
        this.themeServices = themeServices;
    }

    @Override
    public QuestionResponseDTO create(@NotNull QuestionCreateDTO questionCreateDTO) {
        Theme theme = themeServices.findThemeById(questionCreateDTO.getThemeId());
        Question question = questionMapper.toEntity(questionCreateDTO);
        question.setThemes(theme);

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

        Question updatedQuestion = questionRepository.save(currentQuestion);
        return questionMapper.toQuestionResponseDTO(updatedQuestion);
    }

    @Override
    public void delete(long id) {
        Question question = findQuestionById(id);
        questionRepository.delete(question);
    }
}

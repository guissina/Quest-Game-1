package com.quest.services.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quest.dto.rest.Question.QuestionCreateDTO;
import com.quest.dto.rest.Question.QuestionResponseDTO;
import com.quest.dto.rest.Question.QuestionUpdateDTO;
import com.quest.dto.rest.questionOptions.QuestionOptionCreateDTO;
import com.quest.interfaces.rest.IQuestionServices;
import com.quest.mappers.QuestionMapper;
import com.quest.mappers.QuestionOptionsMapper;
import com.quest.models.Question;
import com.quest.models.QuestionOption;
import com.quest.models.Theme;
import com.quest.repositories.QuestionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Service
public class QuestionServices implements IQuestionServices {
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final ThemeServices themeServices;
    private final QuestionOptionsMapper questionOptionsMapper;

    @Autowired
    public QuestionServices(QuestionMapper questionMapper, QuestionRepository questionRepository, ThemeServices themeServices,
            QuestionOptionsMapper questionOptionsMapper) {
        this.questionOptionsMapper = questionOptionsMapper;
        this.questionMapper = questionMapper;
        this.questionRepository = questionRepository;
        this.themeServices = themeServices;
    }

    @Override
    @Transactional
    public QuestionResponseDTO create(@NotNull QuestionCreateDTO questionCreateDTO) {
        Theme theme = themeServices.findThemeById(questionCreateDTO.getThemeId());

        Question question = questionMapper.toEntity(questionCreateDTO);
        question.setTheme(theme);

        // garante lista limpa
        question.getOptions().clear();

        // 2) use o mapper injetado, não de forma estática
        for (QuestionOptionCreateDTO dto : questionCreateDTO.getOptions()) {
            QuestionOption option = questionOptionsMapper.toEntity(dto);
            option.setQuestion(question);
            question.getOptions().add(option);
        }

        // aqui a Question e as QuestionOption são salvas em cascata
        Question saved = questionRepository.save(question);

        return questionMapper.toQuestionResponseDTO(saved);
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
    public QuestionResponseDTO update(@NotNull QuestionUpdateDTO dto) {
        Question existing = findQuestionById(dto.getId());
        // campos simples
        existing.setQuestionText(dto.getQuestionText());
        existing.setDifficulty(dto.getDifficulty());
        // remover e recriar opções
        existing.getOptions().clear();
        dto.getOptions().forEach(optDto -> {
            QuestionOption opt = new QuestionOption();
            opt.setOptionText(optDto.getOptionText());
            opt.setCorrect(optDto.isCorrect());
            opt.setQuestion(existing);
            existing.getOptions().add(opt);
        });
        Question saved = questionRepository.save(existing);
        return questionMapper.toQuestionResponseDTO(saved);
    }

    @Override
    public void delete(long id) {
        Question question = findQuestionById(id);
        questionRepository.delete(question);
    }
}

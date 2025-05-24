package com.quest.services.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
public class QuestionServices implements IQuestionServices {
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final ThemeServices themeServices;
    private final QuestionOptionsMapper questionOptionsMapper;

    @Autowired
    public QuestionServices(QuestionMapper questionMapper, QuestionRepository questionRepository,
            ThemeServices themeServices,
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

        List<QuestionOptionCreateDTO> options = questionCreateDTO.getOptions();

        if (options.size() < 2 || options.size() > 4)
            throw new IllegalArgumentException("A question must have at least two options and no more than 5.");

        long correctCount = options.stream()
                .filter(QuestionOptionCreateDTO::isCorrect)
                .count();

        if (correctCount != 1)
            throw new IllegalArgumentException("Question must have exactly one correct option.");

        Question question = questionMapper.toEntity(questionCreateDTO);
        question.setTheme(theme);

        question.getOptions().clear();

        for (QuestionOptionCreateDTO dto : options) {
            QuestionOption option = questionOptionsMapper.toEntity(dto);
            option.setQuestion(question);
            question.getOptions().add(option);
        }

        Question saved = questionRepository.save(question);
        return questionMapper.toQuestionResponseDTO(saved);
    }

    @Override
    @Transactional
    public List<QuestionResponseDTO> createMany(List<@Valid QuestionCreateDTO> questionCreateDTOList) {
        List<Question> questionsToSave = new ArrayList<>();

        for (QuestionCreateDTO dto : questionCreateDTOList) {
            Theme theme = themeServices.findThemeById(dto.getThemeId());

            Question question = questionMapper.toEntity(dto);
            question.setTheme(theme);

            question.getOptions().clear();

            for (QuestionOptionCreateDTO optionDTO : dto.getOptions()) {
                QuestionOption option = questionOptionsMapper.toEntity(optionDTO);
                option.setQuestion(question);
                question.getOptions().add(option);
            }

            questionsToSave.add(question);
        }

        List<Question> savedQuestions = questionRepository.saveAll(questionsToSave);

        return savedQuestions.stream()
                .map(questionMapper::toQuestionResponseDTO)
                .collect(Collectors.toList());
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
        int optionCount = dto.getOptions().size();
        if (optionCount < 2 || optionCount > 4) {
            throw new IllegalArgumentException(
                    String.format("Question must have between 2 and 4 options (found %d).", optionCount));
        }

        long correctCount = dto.getOptions().stream()
                .filter(opt -> opt.isCorrect())
                .count();

        if (correctCount != 1)
            throw new IllegalArgumentException("Question must have exactly one correct option.");

        Question existing = findQuestionById(dto.getId());

        existing.setQuestionText(dto.getQuestionText());
        existing.setDifficulty(dto.getDifficulty());

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
    public Question findRandomByTheme(Long themeId) {
        themeServices.findThemeById(themeId);

        return questionRepository
                .findRandomByThemeId(themeId)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma questão encontrada para o tema id=" + themeId));
    }

    @Override
    public void delete(long id) {
        Question question = findQuestionById(id);
        questionRepository.delete(question);
    }
}

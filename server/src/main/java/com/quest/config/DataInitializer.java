package com.quest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.quest.dto.rest.Question.QuestionsWithThemeDTO;
import com.quest.dto.rest.Board.BoardCreateDTO;
import com.quest.dto.rest.Player.PlayerCreateDTO;
import com.quest.interfaces.rest.IBoardServices;
import com.quest.interfaces.rest.IPlayerServices;
import com.quest.interfaces.rest.IQuestionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
//@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    private final IPlayerServices playerService;
    private final IBoardServices boardService;
    private final IQuestionServices questionService;

    private final ObjectMapper objectMapper;

    private final PathMatchingResourcePatternResolver resolver =
            new PathMatchingResourcePatternResolver();

    @Autowired
    public DataInitializer(IPlayerServices playerService,
                           IBoardServices boardService,
                           IQuestionServices questionService) {
        this.playerService   = playerService;
        this.boardService    = boardService;
        this.questionService = questionService;
        this.objectMapper    = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Thread.sleep(2000);
        if (questionService.count() == 0)
            loadQuestionsWithTheme();
        if (boardService.count() == 0)
            loadBoards();
        if (playerService.count() == 0)
            loadPlayers();
    }

    private void loadQuestionsWithTheme() throws Exception {
        Resource[] files = resolver.getResources("classpath:json/themes/*.json");
        for (Resource r : files) {
            QuestionsWithThemeDTO dto = objectMapper.readValue(
                    r.getInputStream(),
                    QuestionsWithThemeDTO.class
            );

            questionService.createQuestionsWithTheme(dto);
            System.out.println(">> Tema+Questões carregados de: " + r.getFilename());
        }
    }

    private void loadBoards() throws Exception {
        Resource[] files = resolver.getResources("classpath:json/boards/*.json");
        for (Resource r : files) {
            BoardCreateDTO dto = objectMapper.readValue(
                    r.getInputStream(),
                    BoardCreateDTO.class
            );
            boardService.createBoard(dto);
            System.out.println(">> Board carregado de: " + r.getFilename());
        }
    }

    private void loadPlayers() throws Exception {
        Resource[] files = resolver.getResources("classpath:json/players/*.json");
        for (Resource r : files) {
            PlayerCreateDTO dto = objectMapper.readValue(
                    r.getInputStream(),
                    PlayerCreateDTO.class
            );
            playerService.create(dto);
            System.out.println(">> Player carregado de: " + r.getFilename());
        }
    }
}

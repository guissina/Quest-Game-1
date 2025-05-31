package com.quest.services.ws;

import com.quest.config.websocket.WsDestinations;
import com.quest.dto.ws.Game.AnswerRequestDTO;
import com.quest.dto.ws.Game.EngineStateDTO;
import com.quest.dto.ws.Game.QuestionRequestDTO;
import com.quest.engine.core.GameEngine;
import com.quest.engine.managers.GameSessionManager;
import com.quest.interfaces.rest.IQuestionServices;
import com.quest.models.Question;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameSessionManager sessionManager;
    private final SimpMessagingTemplate messagingTemplate;
    private final IQuestionServices questionService;
    private final QuestionTimerService questionTimerService;

    @Autowired
    public GameService(GameSessionManager sessionManager,
                       SimpMessagingTemplate messagingTemplate,
                       IQuestionServices questionService,
                       QuestionTimerService questionTimerService) {
        this.sessionManager = sessionManager;
        this.messagingTemplate = messagingTemplate;
        this.questionService = questionService;
        this.questionTimerService = questionTimerService;
    }

    public void broadcastGameState(String sessionId, GameEngine engine) {
        EngineStateDTO stateDto = EngineStateDTO.from(sessionId, engine);
        String destination = String.format(WsDestinations.GAME_STATE, sessionId);
        messagingTemplate.convertAndSend(destination, stateDto);
    }

    public EngineStateDTO getGameState(String sessionId) {
        GameEngine engine = sessionManager.getEngine(sessionId);
        return EngineStateDTO.from(sessionId, engine);
    }

    @Transactional
    public void drawQuestion(String sessionId, QuestionRequestDTO req) {
        GameEngine engine = sessionManager.getEngine(sessionId);
        Question question;
        do {
            question = questionService.findRandomByTheme(req.themeId());
            System.out.println("Peguei: " + question.getId()); // TODO Apenas para teste
        } while (false && engine.hasUsedQuestion(question.getId()));
        Hibernate.initialize(question.getOptions());

        engine.registerQuestionFor(req.playerId(), question);
        broadcastGameState(sessionId, engine);

        int QUESTION_TIMEOUT_SEC = 5; // TODO Mover essa logica para outro lugar
        questionTimerService.startQuestionTimer(sessionId, req.playerId(), QUESTION_TIMEOUT_SEC);
    }

    public void answerQuestion(String sessionId, AnswerRequestDTO req) {
        GameEngine engine = sessionManager.getEngine(sessionId);
        engine.answerQuestion(req.playerId(), req.selectedOptionId(), req.steps());
        broadcastGameState(sessionId, engine);
    }
}

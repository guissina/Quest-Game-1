package com.quest.services.ws;

import com.quest.config.websocket.WsDestinations;
import com.quest.dto.rest.Question.QuestionResponseDTO;
import com.quest.dto.ws.Game.AnswerRequestDTO;
import com.quest.dto.ws.Game.MoveRequestDTO;
import com.quest.dto.ws.Game.EngineStateDTO;
import com.quest.engine.core.GameEngine;
import com.quest.engine.core.GameSession;
import com.quest.engine.managers.GameSessionManager;
import com.quest.interfaces.rest.IQuestionServices;
import com.quest.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameSessionManager sessionManager;
    private final SimpMessagingTemplate messagingTemplate;
    private final IQuestionServices questionService;

    @Autowired
    public GameService(GameSessionManager sessionManager,
                       SimpMessagingTemplate messagingTemplate,
                       IQuestionServices questionService) {
        this.sessionManager = sessionManager;
        this.messagingTemplate = messagingTemplate;
        this.questionService = questionService;
    }

    private void broadcastGameState(String sessionId, GameEngine engine) {
        EngineStateDTO stateDto = EngineStateDTO.from(sessionId, engine);
        String destination = String.format(WsDestinations.GAME_STATE, sessionId);
        messagingTemplate.convertAndSend(destination, stateDto);
    }

    public void getGameState(String sessionId) {
        GameEngine engine = sessionManager.getEngine(sessionId);
        broadcastGameState(sessionId, engine);
    }

    public void movePlayer(String sessionId, MoveRequestDTO req) {
        GameEngine engine = sessionManager.getEngine(sessionId);

        engine.move(req.playerId(), req.tileId());
        broadcastGameState(sessionId, engine);
    }

    public void drawQuestion(Long themeId) {
        // TODO Buscar na service com o theme id
        QuestionResponseDTO question = new QuestionResponseDTO();
        messagingTemplate.convertAndSend(WsDestinations.GAME_STATE, question);
    }

    public void answerQuestion(String sessionId, AnswerRequestDTO req) {
        GameEngine engine = sessionManager.getEngine(sessionId);
        Question question = questionService.findQuestionById(req.questionId());

        boolean correct = engine.answerQuestion(req.playerId(), question, req.selectedOptionId(), req.steps());
        // TODO Mecanica de acertar a questao
        broadcastGameState(sessionId, engine);
    }
}

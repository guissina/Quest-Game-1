package com.quest.services.ws;

import com.quest.interfaces.ws.IGameRoomService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.quest.config.websocket.WsDestinations;
import com.quest.dto.ws.Game.AnswerRequestDTO;
import com.quest.dto.ws.Game.EngineStateDTO;
import com.quest.dto.ws.Game.QuestionRequestDTO;
import com.quest.dto.ws.Game.UseAbilityRequestDTO;
import com.quest.engine.core.GameEngine;
import com.quest.engine.managers.GameSessionManager;
import com.quest.engine.state.PlayerState;
import com.quest.interfaces.rest.IQuestionServices;
import com.quest.models.Question;

import jakarta.transaction.Transactional;

@Service
public class GameService {

    private final GameSessionManager sessionManager;
    private final SimpMessagingTemplate messagingTemplate;
    private final IGameRoomService gameRoomService;
    private final IQuestionServices questionService;
    private final TimerService timerService;

    @Autowired
    public GameService(GameSessionManager sessionManager,
            SimpMessagingTemplate messagingTemplate,
            IQuestionServices questionService,
            IGameRoomService gameRoomService,
            TimerService timerService) {
        this.sessionManager = sessionManager;
        this.messagingTemplate = messagingTemplate;
        this.gameRoomService = gameRoomService;
        this.questionService = questionService;
        this.timerService = timerService;
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
            System.out.println("Peguei: " + question.getId()); //

        } while (engine.hasUsedQuestion(question.getId()));
        Hibernate.initialize(question.getOptions());

        engine.prepareQuestion(req.playerId(), question, req.steps());
        broadcastGameState(sessionId, engine);

        timerService.cancelTurnTimer(sessionId, req.playerId());
        timerService.startQuestionTimer(sessionId, req.playerId(), question.getDifficulty().getTimeLimitInSeconds());
    }

    public void answerQuestion(String sessionId, AnswerRequestDTO req) {
        GameEngine engine = sessionManager.getEngine(sessionId);
        engine.answerQuestion(req.playerId(), req.selectedOptionId());
        broadcastGameState(sessionId, engine);

        timerService.cancelQuestionTimer(sessionId, req.playerId());
        if (engine.isFinished()) {
            sessionManager.getSession(sessionId).endGame();
            gameRoomService.broadcastRoomState(sessionId, true);
            return;
        }
        Long nextPlayer = engine.getTurnManager().getCurrentPlayerId();
        timerService.startTurnTimer(sessionId, nextPlayer, 60);
    }

    public void useAbility(String sessionId, UseAbilityRequestDTO req) {
        GameEngine engine = sessionManager.getEngine(sessionId);
        PlayerState playerState = engine.getPlayerState(req.playerId());

        if (playerState == null) {
            throw new IllegalArgumentException("Player not found");
        }

        if (playerState.hasAbility(req.abilityType())) {
            if (playerState.isAbilityActive(req.abilityType())) {
                playerState.deactivateAbility(req.abilityType());
            } else {
                playerState.activateAbility(req.abilityType());
            }
        }

        broadcastGameState(sessionId, engine);
    }
}

package com.quest.controllers.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.quest.dto.ws.Game.AnswerRequestDTO;
import com.quest.dto.ws.Game.EngineStateDTO;
import com.quest.dto.ws.Game.QuestionRequestDTO;
import com.quest.dto.ws.Game.UseAbilityRequestDTO;
import com.quest.services.ws.GameService;

@Controller
public class GameWsController {

    private final GameService gameService;

    @Autowired
    public GameWsController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/game/{sessionId}/state")
    @SendToUser("/queue/game-state")
    public EngineStateDTO getState(@DestinationVariable String sessionId) {
        return gameService.getGameState(sessionId);
    }

    @MessageMapping("/game/{sessionId}/draw")
    public void drawQuestion(@DestinationVariable String sessionId, QuestionRequestDTO req) {
        gameService.drawQuestion(sessionId, req);
    }

    @MessageMapping("/game/{sessionId}/answer")
    public void answer(@DestinationVariable String sessionId, AnswerRequestDTO payload) {
        gameService.answerQuestion(sessionId, payload);
    }

    @MessageMapping("/game/{sessionId}/use-ability")
    public void useAbility(@DestinationVariable String sessionId, UseAbilityRequestDTO req) {
        gameService.useAbility(sessionId, req);
    }
}

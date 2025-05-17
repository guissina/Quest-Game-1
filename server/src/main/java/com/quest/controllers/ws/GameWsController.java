package com.quest.controllers.ws;

import com.quest.dto.ws.Game.MoveRequestDTO;
import com.quest.dto.ws.Game.AnswerRequestDTO;
import com.quest.services.ws.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameWsController {

    private final GameService gameService;

    @Autowired
    public GameWsController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/app/game/{sessionId}/move")
    public void move(@DestinationVariable String sessionId, MoveRequestDTO payload) {
        gameService.movePlayer(sessionId, payload);
    }

    @MessageMapping("/app/game/{sessionId}/answer")
    public void answer(@DestinationVariable String sessionId, AnswerRequestDTO payload) {
        gameService.answerQuestion(sessionId, payload);
    }
}

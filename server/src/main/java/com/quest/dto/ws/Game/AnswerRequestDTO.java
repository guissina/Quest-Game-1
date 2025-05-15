package com.quest.dto.ws.Game;

public class AnswerRequestDTO {

    private Long playerId;
    private String answer;

    public AnswerRequestDTO() {}

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

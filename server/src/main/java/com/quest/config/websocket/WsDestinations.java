package com.quest.config.websocket;

public final class WsDestinations {

    public static final String ROOM_STATE = "/topic/room/%s/state";
    public static final String GAME_STATE = "/topic/game/%s/state";
    public static final String QUESTION_TIMER_TICK = "/topic/game/%s/question-timer/tick";

    private WsDestinations() {}
}

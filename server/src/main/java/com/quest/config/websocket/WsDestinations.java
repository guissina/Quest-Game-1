package com.quest.config.websocket;

public final class WsDestinations {

    public static final String ROOM_PLAYERS = "/topic/room/%s/players";
    public static final String ROOM_START = "/topic/room/%s/start";
    public static final String ROOM_CLOSED = "/topic/room/%s/closed";

    public static final String GAME_STATE = "/topic/game/%s/state";
    public static final String GAME_ERRORS = "/user/queue/game-errors";

    private WsDestinations() {}
}

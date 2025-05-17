package com.quest.config.websocket;

public final class WsDestinations {

    public static final String ROOM_STATE = "/topic/room/%s/state";
    public static final String GAME_STATE = "/topic/game/%s/state";

    private WsDestinations() {}
}

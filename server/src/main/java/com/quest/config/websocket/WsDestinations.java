package com.quest.config.websocket;

public final class WsDestinations {

    public static final String ROOM_PLAYERS = "/topic/room/%s/players";
    public static final String ROOM_START   = "/topic/room/%s/start";

    private WsDestinations() {}
}

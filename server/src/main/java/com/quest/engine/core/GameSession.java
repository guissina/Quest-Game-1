package com.quest.engine.core;

public class GameSession {

    private final String sessionId;
    private final GameRoom room;
    private GameEngine engine;

    public GameSession(String sessionId) {
        this.sessionId = sessionId;
        this.room = new GameRoom();
    }

    public String getSessionId() {
        return sessionId;
    }

    public GameRoom getRoom()     {
        return room;
    }

    public boolean isStarted()    {
        return engine != null;
    }

    public void startGame(GameEngine engine) {
        if (this.engine != null) return;
        this.engine = engine;
        room.markStarted();
    }

    public GameEngine getEngine() {
        if (engine == null)
            throw new IllegalStateException("Game not started");
        return engine;
    }
}



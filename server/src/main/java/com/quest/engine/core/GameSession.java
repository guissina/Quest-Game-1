package com.quest.engine.core;

import com.quest.models.Player;

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

    public GameEngine getEngine() {
        if (engine == null)
            throw new IllegalStateException("Game not started");
        return engine;
    }

    public boolean isStarted()    {
        return engine != null;
    }

    public void startGame(GameEngine engine) {
        if (isStarted()) return;
        this.engine = engine;
        room.markStarted();
    }

    public void joinPlayer(Player player) {
        boolean ok = room.join(player);
        if (!ok) throw new IllegalStateException("Sala cheia ou já iniciada");
        if (isStarted())
            engine.joinGame(player);
    }

    public void leavePlayer(Long playerId) {
        room.leave(playerId);
        if (isStarted())
            engine.leaveGame(playerId);
        if (room.getPlayers().isEmpty())
            this.engine = null;
    }
}

package com.quest.engine.core;

import com.quest.models.Player;

public class GameSession {

    private final String sessionId;
    private final GameRoom room;
    private GameEngine engine;
    private Boolean publicSession;

    public GameSession(String sessionId, Boolean publicSession) {
        this.sessionId = sessionId;
        this.room = new GameRoom();
        this.publicSession = publicSession;
    }

    public String getSessionId() {
        return sessionId;
    }

    public GameRoom getRoom() {
        return room;
    }

    public GameEngine getEngine() {
        if (engine == null)
            throw new IllegalStateException("Game not started");
        return engine;
    }

    public boolean isStarted() {
        return engine != null;
    }

    public Boolean getPublicSession() {
        return publicSession;
    }

    public void setPublicSession(Boolean publicSession) {
        this.publicSession = publicSession;
    }

    public void startGame(GameEngine engine) {
        if (isStarted())
            return;
        this.engine = engine;
        room.markStarted();
    }

    public void joinPlayer(Player player) {
        boolean ok = room.join(player);
        if (!ok)
            throw new IllegalStateException("Sala cheia ou já iniciada");
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

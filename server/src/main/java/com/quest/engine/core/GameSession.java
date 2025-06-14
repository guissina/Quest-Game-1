package com.quest.engine.core;

import com.quest.models.Player;

public class GameSession {

    private final String sessionId;
    private final GameRoom room;
    private GameEngine engine;
    private Boolean publicSession;
    private Long hostId;

    public GameSession(String sessionId, Boolean publicSession, Long hostId) {
        this.sessionId = sessionId;
        this.room = new GameRoom();
        this.publicSession = publicSession;
        this.hostId = hostId;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
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

    public boolean isPublicSession() {
        return publicSession;
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

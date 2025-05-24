package com.quest.engine.core;

import com.quest.models.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameRoom {

    private final List<Player> players = new CopyOnWriteArrayList<>();
    private boolean started = false;

    public List<Player> getPlayers() { return players; }

    public boolean join(Player p) {
        if (started || players.size() >= 4) return false;
        return players.add(p);
    }

    public void leave(Long playerId) {
        players.removeIf(p -> p.getId().equals(playerId));
    }

    public void markStarted() {
        this.started = true;
    }

    public boolean isStarted() {
        return started;
    }
}

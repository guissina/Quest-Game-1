package com.quest.engine.core;

import com.quest.models.Player;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameRoom {

    private final List<Player> players = new CopyOnWriteArrayList<>();
    private boolean started = false;

    public List<Player> getPlayers() { return players; }

    public Optional<Player> findPlayerById(Long playerId) {
        return players.stream()
                .filter(p -> p.getId().equals(playerId))
                .findFirst();
    }

    public boolean addPlayer(Player p) {
        if (players.size() >= 4 || started) return false;
        return players.add(p);
    }

    public void removePlayer(Long playerId) {
        players.removeIf(p -> p.getId().equals(playerId));
    }

    public void markStarted() {
        this.started = true;
    }

    public boolean isStarted() {
        return started;
    }
}

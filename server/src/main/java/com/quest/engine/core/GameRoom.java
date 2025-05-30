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

    public boolean join(Player player) {
        if (findPlayerById(player.getId()).isPresent())
            throw new IllegalArgumentException("Player already in room.");
        if (started || players.size() >= 6)
            throw new IllegalArgumentException("Room is full.");
        return players.add(player);
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

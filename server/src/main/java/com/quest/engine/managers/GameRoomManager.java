package com.quest.engine.managers;

import com.quest.engine.core.GameRoom;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameRoomManager {

    private final Map<String, GameRoom> rooms = new ConcurrentHashMap<>();

    public void createRoom(String roomId) {
        rooms.put(roomId, new GameRoom(roomId));
    }

    public GameRoom getRoom(String roomId) {
        GameRoom room = rooms.get(roomId);
        if (room == null) 
            throw new IllegalArgumentException("Room not found");
        return room;
    }

    public void removeRoom(String roomId) {
        rooms.remove(roomId);
    }
}

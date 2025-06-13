package com.quest.dto.ws.Room;

public record RoomCreateRequestDTO(
        Long creatorPlayerId,
        Integer maxPlayers,
        Boolean publicSession
        ) {}

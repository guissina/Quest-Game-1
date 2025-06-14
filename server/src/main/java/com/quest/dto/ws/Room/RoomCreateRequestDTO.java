package com.quest.dto.ws.Room;

public record RoomCreateRequestDTO(
        Boolean publicSession,
        Long hostId
) {}

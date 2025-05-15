package com.quest.dto.ws.Room;

public class PlayerRoomResponseDTO {

    private Long id;
    private String name;

    public PlayerRoomResponseDTO(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
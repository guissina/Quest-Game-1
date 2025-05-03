package com.Quest.quest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Quest.quest.models.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    
}

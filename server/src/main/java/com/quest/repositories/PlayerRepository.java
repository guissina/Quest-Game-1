package com.quest.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quest.models.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findById(long playerId);

    Optional<Player> findByName(String name);

    Optional<Player> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByName(String name);

    Boolean existsByEmailAndIdNot(String email, Long id);

    Boolean existsByNameAndIdNot(String name, Long id);
    // List<Player> findAll();
}

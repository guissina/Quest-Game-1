package com.quest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quest.models.PlayerTheme;

public interface PlayerThemeRepository extends JpaRepository<PlayerTheme, Long> {
    Boolean existsByPlayerIdAndThemeId(Long playerId, Long themeId);

    List<PlayerTheme> findPlayerById(Long playerId);
}

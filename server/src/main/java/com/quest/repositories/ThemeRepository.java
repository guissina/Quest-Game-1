package com.quest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quest.models.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Theme findByName(String name);

    Theme findByCode(String code);

    List<Theme> findAllByFreeTrue();
}

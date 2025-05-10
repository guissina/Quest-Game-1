package com.quest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quest.models.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

}

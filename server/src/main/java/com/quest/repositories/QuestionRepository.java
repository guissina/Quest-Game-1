package com.quest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quest.models.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // TODO Esse método gera muito acoplamento, pois RAND é apenas alguns BDs enquanto outros usam RANDOM
    @Query(
            value = "SELECT * FROM questions WHERE theme_id = :themeId ORDER BY RAND() LIMIT 1",
            nativeQuery = true
    )
    Optional<Question> findRandomByThemeId(@Param("themeId") Long themeId);
}

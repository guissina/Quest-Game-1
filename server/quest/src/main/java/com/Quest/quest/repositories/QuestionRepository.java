package com.Quest.quest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Quest.quest.models.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}

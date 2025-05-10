package com.quest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quest.models.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}

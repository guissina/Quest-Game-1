package com.quest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quest.models.QuestionOption;

public interface QuestionOptionsRepository extends JpaRepository<QuestionOption, Long> {

}

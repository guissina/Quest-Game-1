package com.quest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quest.models.SpecialCard;

public interface SpecialCardRepository extends JpaRepository<SpecialCard, Long> {

}

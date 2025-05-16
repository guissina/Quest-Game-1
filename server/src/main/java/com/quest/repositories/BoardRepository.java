package com.quest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quest.models.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // Optional<Board> findById(long boardId);
    // Optional<Board> findByName(String name);
    // Optional<Board> findByEmail(String email);
    // List<Board> findAll();

}

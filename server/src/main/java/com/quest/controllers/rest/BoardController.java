package com.quest.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quest.dto.rest.Board.BoardCreateDTO;
import com.quest.dto.rest.Board.BoardResponseDTO;
import com.quest.dto.rest.Board.BoardUpdateDTO;
import com.quest.services.rest.BoardServices;

@RestController
@RequestMapping("/boards")
public class BoardController {
    private final BoardServices boardServices;

    @Autowired
    public BoardController(BoardServices boardServices) {
        this.boardServices = boardServices;
    }

    @PostMapping
    public ResponseEntity<BoardResponseDTO> createBoard(@RequestBody BoardCreateDTO boardCreateDTO) {
        BoardResponseDTO createdBoard = boardServices.createBoard(boardCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> updateBoard(@PathVariable Long id,
            @RequestBody BoardUpdateDTO boardUpdateDTO) {
        boardUpdateDTO.setId(id);
        BoardResponseDTO updatedBoard = boardServices.updateBoard(boardUpdateDTO);
        return ResponseEntity.ok(updatedBoard);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> getBoardById(@PathVariable Long id) {
        BoardResponseDTO board = boardServices.findById(id);
        return ResponseEntity.ok(board);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> getAllBoards() {
        List<BoardResponseDTO> boards = boardServices.findAll();
        return ResponseEntity.ok(boards);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardServices.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }

}

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/boards")
@Tag(name = "Board", description = "Board management")
public class BoardController {
    private final BoardServices boardServices;

    @Autowired
    public BoardController(BoardServices boardServices) {
        this.boardServices = boardServices;
    }

    @PostMapping
    @Operation(summary = "Create a new board")
    public ResponseEntity<BoardResponseDTO> createBoard(@RequestBody BoardCreateDTO boardCreateDTO) {
        BoardResponseDTO createdBoard = boardServices.createBoard(boardCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing board")
    public ResponseEntity<BoardResponseDTO> updateBoard(@PathVariable Long id,
            @RequestBody BoardUpdateDTO boardUpdateDTO) {
        boardUpdateDTO.setId(id);
        BoardResponseDTO updatedBoard = boardServices.updateBoard(boardUpdateDTO);
        return ResponseEntity.ok(updatedBoard);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a board by ID")
    public ResponseEntity<BoardResponseDTO> getBoardById(@PathVariable Long id) {
        BoardResponseDTO board = boardServices.findById(id);
        return ResponseEntity.ok(board);
    }

    @GetMapping
    @Operation(summary = "Get all boards")
    public ResponseEntity<List<BoardResponseDTO>> getAllBoards() {
        List<BoardResponseDTO> boards = boardServices.findAll();
        return ResponseEntity.ok(boards);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a board by ID")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardServices.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }

}

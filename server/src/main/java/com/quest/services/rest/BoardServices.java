package com.quest.services.rest;

import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quest.dto.rest.Board.BoardCreateDTO;
import com.quest.dto.rest.Board.BoardResponseDTO;
import com.quest.dto.rest.Board.BoardUpdateDTO;
import com.quest.interfaces.rest.IBoardServices;
import com.quest.mappers.BoardMapper;
import com.quest.models.Board;
import com.quest.repositories.BoardRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BoardServices implements IBoardServices {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    @Autowired
    public BoardServices(BoardRepository boardRepository, TileRepository tileRepository, BoardMapper boardMapper) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
    }

    @Override
    public BoardResponseDTO createBoard(BoardCreateDTO boardCreateDTO) {
        Board board = boardMapper.toBoardCreate(boardCreateDTO);
        Board savedBoard = boardRepository.save(board);
        return boardMapper.toBoardResponseDTO(savedBoard);
    }

    @Override
    public Board findBoardById(long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: " + id));
    }

    @Override
    public BoardResponseDTO findById(long id) {
        Board board = findBoardById(id);
        return boardMapper.toBoardResponseDTO(board);
    }

    @Override
    public BoardResponseDTO updateBoard(BoardUpdateDTO boardUpdateDTO) {
        Board currentBoard = findBoardById(boardUpdateDTO.getId());
        boardMapper.toBoardUpdate(boardUpdateDTO);
        Board updatedBoard = boardRepository.save(currentBoard);
        return boardMapper.toBoardResponseDTO(updatedBoard);
    }

    @Override
    @Transactional
    public void deleteBoard(long id) {
        Board board = findBoardById(id);
        boardRepository.delete(board);
    }
}

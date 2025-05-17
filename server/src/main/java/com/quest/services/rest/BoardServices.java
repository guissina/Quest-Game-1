package com.quest.services.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quest.dto.rest.Board.BoardCreateDTO;
import com.quest.dto.rest.Board.BoardResponseDTO;
import com.quest.dto.rest.Board.BoardUpdateDTO;
import com.quest.interfaces.rest.IBoardServices;
import com.quest.mappers.BoardMapper;
import com.quest.models.Board;
import com.quest.models.Tile;
import com.quest.repositories.BoardRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BoardServices implements IBoardServices {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    @Autowired
    public BoardServices(BoardRepository boardRepository, BoardMapper boardMapper) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
    }

    @Override
    public BoardResponseDTO createBoard(BoardCreateDTO boardCreateDTO) {
        // 1. Mapeia DTO para entidade Board (sem tiles ainda)
        Board board = boardMapper.toBoardCreate(boardCreateDTO);

        // 2. Gera todos os tiles com base em rows x cols
        List<Tile> tiles = new ArrayList<>();
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                Tile tile = new Tile();
                tile.setRow(r);
                tile.setCol(c);
                tile.setBoard(board); // VERY IMPORTANT: vincula o tile ao board
                tiles.add(tile);
            }
        }
        board.setTiles(tiles); // adiciona a lista de tiles no board

        // 3. Salva o board (vai persistir também todos os tiles, pelo CascadeType.ALL)
        Board savedBoard = boardRepository.save(board);

        // 4. Retorna DTO de resposta
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
    public List<BoardResponseDTO> findAll() {
        List<Board> boards = boardRepository.findAll();
        return boardMapper.toBoardResponseDTOs(boards);
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

package com.quest.services.rest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.quest.dto.rest.tile.TileCreateDTO;
import com.quest.mappers.TileMapper;
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
    private final TileMapper tileMapper;

    @Autowired
    public BoardServices(BoardRepository boardRepository, BoardMapper boardMapper, TileMapper tileMapper) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
        this.tileMapper = tileMapper;
    }

    private void validateTiles(BoardCreateDTO dto) {
        List<TileCreateDTO> tiles = dto.getTiles();
        int count = tiles.size();

        Set<Integer> actualSequence = tiles.stream()
                .map(TileCreateDTO::getSequence)
                .collect(Collectors.toSet());

        Set<Integer> expectedSequence = IntStream.range(0, count)
                .boxed()
                .collect(Collectors.toSet());

        if (!actualSequence.equals(expectedSequence))
            throw new IllegalArgumentException("Sequências inválidas: espere exatamente " + expectedSequence);

        tiles.forEach(tileDto -> {
            int row = tileDto.getRow();
            int col = tileDto.getCol();

            if (row < 0 || row >= dto.getRows())
                throw new IllegalArgumentException(
                        String.format("Row fora do grid: %d (válido: 0 a %d)", row, dto.getRows() - 1)
                );
            if (col < 0 || col >= dto.getCols())
                throw new IllegalArgumentException(
                        String.format("Coluna fora do grid: %d (válido: 0 a %d)", col, dto.getCols() - 1)
                );
        });

    }

    @Override
    @Transactional
    public BoardResponseDTO createBoard(BoardCreateDTO dto) {
        validateTiles(dto);
        Board board = boardMapper.toEntity(dto);

        List<Tile> tiles = dto.getTiles().stream()
                .map(tileDto -> {
                    Tile tile = tileMapper.toEntity(tileDto);
                    tile.setBoard(board);
                    return tile;
                })
                .toList();
        board.setTiles(tiles);

        Board saved = boardRepository.save(board);
        return boardMapper.toBoardResponseDTO(saved);
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

package com.quest.interfaces.rest;

import com.quest.dto.rest.Board.BoardCreateDTO;
import com.quest.dto.rest.Board.BoardResponseDTO;
import com.quest.dto.rest.Board.BoardUpdateDTO;
import com.quest.models.Board;

public interface IBoardServices {
    BoardResponseDTO createBoard(BoardCreateDTO boardCreateDTO);

    Board findBoardById(long id);

    void deleteBoard(long id);

    BoardResponseDTO updateBoard(BoardUpdateDTO boardUpdateDTO);

    BoardResponseDTO findById(long id);

}

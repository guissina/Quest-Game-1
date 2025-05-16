package com.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.quest.dto.rest.Board.BoardCreateDTO;
import com.quest.dto.rest.Board.BoardResponseDTO;
import com.quest.dto.rest.Board.BoardUpdateDTO;
import com.quest.models.Board;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    BoardResponseDTO toBoardResponseDTO(Board board);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tiles", ignore = true)
    Board toBoardCreate(BoardCreateDTO boardCreateDTO);

    @Mapping(target = "tiles", ignore = true)
    @Mapping(target = "id", ignore = true)
    Board toBoardUpdate(BoardUpdateDTO boardUpdateDTO);

    List<BoardResponseDTO> toBoardResponseDTOs(List<Board> boards);

}

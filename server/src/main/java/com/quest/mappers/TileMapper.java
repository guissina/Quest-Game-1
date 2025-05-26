package com.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.quest.dto.rest.tile.TileCreateDTO;
import com.quest.dto.rest.tile.TileResponseDTO;
import com.quest.models.Board;
import com.quest.models.Tile;

@Mapper(componentModel = "spring", imports = Board.class)
public interface TileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "board.id", ignore = true)
    Tile toEntity(TileCreateDTO dto);

    @Mapping(source = "board.id", target = "boardId")
    @Mapping(target = "specialCard", ignore = true)
    @Mapping(target = "questionTheme", ignore = true)
    TileResponseDTO toResponseDto(Tile entity);

    List<TileResponseDTO> toResposnseDtos(List<Tile> entities);
}
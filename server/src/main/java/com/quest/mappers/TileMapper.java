package com.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.MappingTarget;

import com.quest.dto.rest.tile.TileCreateDTO;
import com.quest.dto.rest.tile.TileUpdateDTO;
import com.quest.dto.rest.tile.TileResponseDTO;
import com.quest.models.Tile;
import com.quest.models.Board;

@Mapper(componentModel = "spring", imports = Board.class)
public interface TileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "boardId", target = "board.id")
    @Mapping(target = "specialCard", ignore = true)
    @Mapping(target = "questionTheme", ignore = true)
    Tile toEntity(TileCreateDTO dto);

    @Mapping(source = "boardId", target = "board.id")
    @Mapping(target = "specialCard", ignore = true)
    @Mapping(target = "questionTheme", ignore = true)
    void updateEntityFromDto(TileUpdateDTO dto, @MappingTarget Tile entity);

    @Mapping(source = "board.id", target = "boardId")
    @Mapping(target = "specialCard", ignore = true)
    @Mapping(target = "questionTheme", ignore = true)
    TileResponseDTO toResponseDto(Tile entity);

    List<TileResponseDTO> toResposnseDtos(List<Tile> entities);

}

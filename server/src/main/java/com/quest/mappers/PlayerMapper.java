package com.quest.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.quest.dto.rest.Player.PlayerBalanceDTO;
import com.quest.dto.rest.Player.PlayerCreateDTO;
import com.quest.dto.rest.Player.PlayerLoginDTO;
import com.quest.dto.rest.Player.PlayerResponseDTO;
import com.quest.dto.rest.Player.PlayerUpdateDTO;
import com.quest.dto.ws.Room.PlayerRoomResponseDTO;
import com.quest.models.Player;

@Mapper(componentModel = "spring", uses = BoardMapper.class)
public interface PlayerMapper {

    // @Mapping(target = "boardIds", source = "boards", qualifiedByName =
    // "boardsToIds")
    PlayerResponseDTO toPlayerResponseDTO(Player player);

    // @Mapping(target = "boards", source = "boardIds", qualifiedByName =
    // "idsToBoards")
    @Mapping(target = "balance", source = "balance")
    Player toEntity(PlayerResponseDTO playerResponseDTO);

    @Mapping(target = "id", ignore = true)
    // @Mapping(target = "boards", source = "boardIds", qualifiedByName =
    // "idsToBoards")
    // @Mapping(target = "balance", source = "balance")
    @Mapping(target = "balance", ignore = true)
    Player toEntity(PlayerCreateDTO playerCreateDTO);

    // @Mapping(target = "boards", source = "boardIds", qualifiedByName =
    // "idsToBoards")
    @Mapping(target = "balance", ignore = true)
    Player toEntity(PlayerUpdateDTO playerUpdateDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    PlayerRoomResponseDTO toPlayerRoomResponseDTO(Player player);

    PlayerBalanceDTO toPlayerBalanceDTO(Player player);

    PlayerLoginDTO toPlayerLoginDTO(Player player);

    List<PlayerRoomResponseDTO> toPlayerRoomResponseDTOs(List<Player> players);

    List<PlayerResponseDTO> toPlayerResponseDTOs(List<Player> players);

    // @Named("boardsToIds")
    // default List<Long> mapBoardsToIds(List<Board> boards) {
    // if (boards == null)
    // return null;
    // return boards.stream().map(Board::getId).collect(Collectors.toList());
    // }

    // @Named("idsToBoards")
    // default List<Board> mapIdsToBoards(List<Long> boardIds) {
    // if (boardIds == null)
    // return null;
    // return boardIds.stream().map(id -> {
    // Board b = new Board();
    // b.setId(id);
    // return b;
    // }).collect(Collectors.toList());
    // }
}

import { Board } from "../models/Board";
import { TileProps } from "../models/Tile";
import { Player } from "../models/Player";
import { Theme } from "../enums/Theme";
import { MovementToken } from "../models/MovementToken";
import { createTokensForPlayer } from "../services/gameServices";

// function createTokensForPlayer(prefix: string): MovementToken[] {
//     return [1, 2, 3, 4, 5].map(v => {
//         const isLost = Math.random() < 0.3; // 30% de chance de estar perdido
//         return new MovementToken(`${prefix}-${v}`, v, isLost);
//     });
// }

const alice = new Player("1", "Alice", 5, createTokensForPlayer("1"));
const bob = new Player("2", "Bob", 3, createTokensForPlayer("2"));
const carol = new Player("3", "Carol", 8, createTokensForPlayer("3"));
const dave = new Player("4", "Dave", 2, createTokensForPlayer("4"));
const eve = new Player("5", "Eve", 7, createTokensForPlayer("5"));

const tileProps1: TileProps = {
    id: "tile-1",
    questionTheme: Theme.MUSIC,
    players: [],
};
const tileProps2: TileProps = {
    id: "tile-2",
    questionTheme: Theme.GAMES,
    players: [alice, bob],
};
const tileProps3: TileProps = {
    id: "tile-3",
    questionTheme: Theme.TECHNOLOGY,
    players: [carol],
};
const tileProps4: TileProps = {
    id: "tile-4",
    questionTheme: Theme.MUSIC,
    players: [dave],
};
const tileProps5: TileProps = {
    id: "tile-5",
    questionTheme: Theme.GAMES,
    players: [eve, alice],
};
const tileProps6: TileProps = {
    id: "tile-6",
    questionTheme: Theme.TECHNOLOGY,
    players: [],
};

const boardA = new Board({
    id: "board-A",
    layoutId: "layout-1",
    tiles: [tileProps1, tileProps2, tileProps3, tileProps4, tileProps5],
});

const boardB = new Board({
    id: "board-B",
    layoutId: "layout-2",
    tiles: [
        tileProps3,
        tileProps6,
        tileProps1,
        tileProps5,
        tileProps4,
        tileProps2,
    ],
});

const MOCK_BOARDS = [boardA, boardB];

export const getBoards = async (): Promise<Board[]> => {
    await new Promise((r) => setTimeout(r, 200));
    return MOCK_BOARDS;
};

export const getBoardById = async (id: string): Promise<Board> => {
    await new Promise((r) => setTimeout(r, 200));
    const found = MOCK_BOARDS.find((b) => b.id === id);
    if (!found) {
        return Promise.reject(new Error(`Board with id="${id}" not found.`));
    }
    return found;
};

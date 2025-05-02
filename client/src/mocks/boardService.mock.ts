import { Board } from "../models/Board";
import { TileProps } from "../models/Tile";
import { Player } from "../models/Player";
import { Theme } from "../enums/Theme";

const alice = new Player("Alice", 5);
const bob = new Player("Bob", 3);
const carol = new Player("Carol", 8);
const dave = new Player("Dave", 2);
const eve = new Player("Eve", 7);

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

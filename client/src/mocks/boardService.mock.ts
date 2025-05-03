import { Board } from "../models/Board";
import { TileProps } from "../models/Tile";
import { Theme } from "../enums/Theme";
import { SpecialCard, SpecialCardProps } from "../models/SpecialCards";

// function createTokensForPlayer(prefix: string): MovementToken[] {
//     return [1, 2, 3, 4, 5].map(v => {
//         const isLost = Math.random() < 0.3; // 30% de chance de estar perdido
//         return new MovementToken(`${prefix}-${v}`, v, isLost);
//     });
// }

const specialCard1: SpecialCardProps = {
    id: "special-1",
    name: "Special Card 1",
    description: "This is a special card.",
    effect: "Some effect",
    type: "type1",
};
const specialCard2: SpecialCardProps = {
    id: "special-2",
    name: "Special Card 2",
    description: "This is another special card.",
    effect: "Some effect",
    type: "type2",
};

const tileProps1: TileProps = {
    id: "tile-1",
    questionTheme: Theme.MUSIC,
    players: [],
};
const tileProps2: TileProps = {
    id: "tile-2",
    questionTheme: Theme.GAMES,
    players: [],
};
const tileProps3: TileProps = {
    id: "tile-3",
    questionTheme: Theme.TECHNOLOGY,
    specialCard: new SpecialCard(specialCard1),
    players: [],
};
const tileProps4: TileProps = {
    id: "tile-4",
    questionTheme: Theme.MUSIC,
    specialCard: new SpecialCard(specialCard2),
    players: [],
};
const tileProps5: TileProps = {
    id: "tile-5",
    questionTheme: Theme.GAMES,
    players: [],
};
const tileProps6: TileProps = {
    id: "tile-6",
    questionTheme: Theme.TECHNOLOGY,
    specialCard: new SpecialCard(specialCard1),
    players: [],
};
const tileProps7: TileProps = {
    id: "tile-7",
    questionTheme: Theme.MUSIC,
    players: [],
};
const tileProps8: TileProps = {
    id: "tile-8",
    questionTheme: Theme.GAMES,
    players: [],
};
const tileProps9: TileProps = {
    id: "tile-9",
    questionTheme: Theme.TECHNOLOGY,
    specialCard: new SpecialCard(specialCard2),
    players: [],
};
const tileProps10: TileProps = {
    id: "tile-10",
    questionTheme: Theme.MUSIC,
    players: [],
};
const tileProps11: TileProps = {
    id: "tile-11",
    questionTheme: Theme.GAMES,
    players: [],
};


const boardA = new Board({
    id: "board-A",
    layoutId: "layout-1",
    tiles: [tileProps1, tileProps2, tileProps3, tileProps4, tileProps5, tileProps6, tileProps7, tileProps8, tileProps9, tileProps10, tileProps11],
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

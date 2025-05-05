import { Board } from "../models/Board";
import { TileProps } from "../models/Tile";
import { CardProps } from "../models/Card";
import { ThemeProps } from "../models/Theme";

const themeMusic: ThemeProps = {
    id: "theme-1",
    code: "MUSIC",
    name: "Música",
    questions: [
        { id: "q-m-1", text: "Quem cantou 'Bohemian Rhapsody'?", answer: "Queen", difficulty: "easy" },
        { id: "q-m-2", text: "Qual banda lançou o álbum 'The Wall'?", answer: "Pink Floyd", difficulty: "medium" },
    ]
};
const themeGames: ThemeProps = {
    id: "theme-2",
    code: "GAMES",
    name: "Jogos",
    questions: [
        { id: "q-g-1", text: "Em que ano foi lançado o primeiro 'Super Mario Bros.'?", answer: "1985", difficulty: "easy" },
        { id: "q-g-2", text: "Qual o nome da arma icônica de 'Halo'?", answer: "Energy Sword", difficulty: "hard" },
    ]
};
const themeTech: ThemeProps = {
    id: "theme-3",
    code: "TECHNOLOGY",
    name: "Tecnologia",
    questions: [
        { id: "q-t-1", text: "Quem fundou a Microsoft?", answer: "Bill Gates e Paul Allen", difficulty: "easy" },
        { id: "q-t-2", text: "O que significa 'HTTP'?", answer: "HyperText Transfer Protocol", difficulty: "medium" },
    ]
};

const specialCard1: CardProps = {
    id: "special-1",
    name: "Carta Especial 1",
    description: "Efeito especial 1",
    effect: "Avança 2 casas",
    type: "move"
};
const specialCard2: CardProps = {
    id: "special-2",
    name: "Carta Especial 2",
    description: "Efeito especial 2",
    effect: "Pule a vez de um oponente",
    type: "skip"
};

const tileProps1: TileProps = { id: "tile-1" };
const tileProps2: TileProps = { id: "tile-2", questionTheme: themeGames };
const tileProps3: TileProps = { id: "tile-3", questionTheme: themeTech };
const tileProps4: TileProps = { id: "tile-4", questionTheme: themeMusic, specialCard: specialCard2 };
const tileProps5: TileProps = { id: "tile-5", questionTheme: themeGames };
const tileProps6: TileProps = { id: "tile-6", questionTheme: themeTech, specialCard: specialCard1 };
const tileProps7: TileProps = { id: "tile-7", questionTheme: themeMusic };
const tileProps8: TileProps = { id: "tile-8", questionTheme: themeGames };
const tileProps9: TileProps = { id: "tile-9", questionTheme: themeTech, specialCard: specialCard2 };
const tileProps10: TileProps = { id: "tile-10", questionTheme: themeMusic };
const tileProps11: TileProps = { id: "tile-11", questionTheme: themeGames };
const tileProps12: TileProps = { id: "tile-12", questionTheme: themeTech };
const tileProps13: TileProps = { id: "tile-13", questionTheme: themeMusic };
const tileProps14: TileProps = { id: "tile-14", questionTheme: themeGames };
const tileProps15: TileProps = { id: "tile-15", questionTheme: themeTech };
const tileProps16: TileProps = { id: "tile-16", questionTheme: themeMusic };

const boardA = new Board({
    id: "board-A",
    layoutId: "layout-1",
    tiles: [
        tileProps1, tileProps2, tileProps3, tileProps4,
        tileProps5, tileProps6, tileProps7, tileProps8,
        tileProps9, tileProps10, tileProps11, tileProps12,
        tileProps13, tileProps14, tileProps15, tileProps16
    ],
});

const boardB = new Board({
    id: "board-B",
    layoutId: "layout-2",
    tiles: [
        tileProps3, tileProps6, tileProps1,
        tileProps5, tileProps4, tileProps2
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

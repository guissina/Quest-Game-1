// src/mocks/boardService.mock.ts
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
    ],
};
const themeGames: ThemeProps = {
    id: "theme-2",
    code: "GAMES",
    name: "Jogos",
    questions: [
        { id: "q-g-1", text: "Em que ano foi lançado o primeiro 'Super Mario Bros.'?", answer: "1985", difficulty: "easy" },
        { id: "q-g-2", text: "Qual o nome da arma icônica de 'Halo'?", answer: "Energy Sword", difficulty: "hard" },
    ],
};
const themeTech: ThemeProps = {
    id: "theme-3",
    code: "TECHNOLOGY",
    name: "Tecnologia",
    questions: [
        { id: "q-t-1", text: "Quem fundou a Microsoft?", answer: "Bill Gates e Paul Allen", difficulty: "easy" },
        { id: "q-t-2", text: "O que significa 'HTTP'?", answer: "HyperText Transfer Protocol", difficulty: "medium" },
    ],
};

const specialCardMove: CardProps = {
    id: "special-move",
    name: "Avança 2",
    description: "Avança 2 casas à frente",
    effect: "move-2",
    type: "move",
};
const specialCardSkip: CardProps = {
    id: "special-skip",
    name: "Pule um",
    description: "Pule a vez de um oponente",
    effect: "skip-turn",
    type: "skip",
};

const tilePropsRing: TileProps[] = [
    // Linha 0: todas as colunas
    { id: "1",  row: 0, col: 0, questionTheme: themeGames },
    { id: "2",  row: 0, col: 1, questionTheme: themeMusic },
    { id: "3",  row: 0, col: 2, questionTheme: themeTech },
    { id: "4",  row: 0, col: 3, questionTheme: themeGames },
    { id: "5",  row: 0, col: 4, questionTheme: themeMusic },
  
    // Linha 1: só coluna 4
    { id: "6",  row: 1, col: 4, questionTheme: themeTech },
  
    // Linha 2: todas as colunas
    { id: "7",  row: 2, col: 0, questionTheme: themeGames },
    { id: "8",  row: 2, col: 1, questionTheme: themeMusic },
    { id: "9",  row: 2, col: 2, questionTheme: themeTech },
    { id: "10", row: 2, col: 3, questionTheme: themeGames },
    { id: "11", row: 2, col: 4, questionTheme: themeMusic },
  
    // Linha 3: só coluna 0
    { id: "12", row: 3, col: 0, questionTheme: themeTech },
  
    // Linha 4: todas as colunas
    { id: "13", row: 4, col: 0, questionTheme: themeGames },
    { id: "14", row: 4, col: 1, questionTheme: themeMusic },
    { id: "15", row: 4, col: 2, questionTheme: themeTech },
    { id: "16", row: 4, col: 3, questionTheme: themeGames },
    { id: "17", row: 4, col: 4, questionTheme: themeMusic },
];

const boardA = new Board({
    id: "board-A",
    rows: 4,
    cols: 4,
    tiles: tilePropsRing,
});

const MOCK_BOARDS = [boardA];

export const getBoards = async (): Promise<Board[]> => {
    await new Promise((r) => setTimeout(r, 200));
    return MOCK_BOARDS;
};

export const getBoardById = async (id: string): Promise<Board> => {
    await new Promise((r) => setTimeout(r, 200));
    const found = MOCK_BOARDS.find((b) => b.id === id);
    if (!found) throw new Error(`Board with id="${id}" not found.`);
    return found;
};

import { useState } from "react";
import { Game } from "../models/Game";
import { Player } from "../models/Player";
import { Board } from "../models/Board";

export const useGame = () => {
    const [game, setGame] = useState<Game | null>(null);

    const startGame = (board: Board, players: Player[]) => {
        const newGame = new Game({
            id: crypto.randomUUID(),
            board,
            players,
        });
        newGame.seedPlayers();
        setGame(newGame);
    };

    const updateGame = (callback: (g: Game) => void) => {
        if (!game) return;
        const cloned = game.clone();
        callback(cloned);
        setGame(cloned);
    };

    const movePlayerToTile = (playerId: string, toTileId: string) => {
        updateGame((g) => g.moveToTile(playerId, toTileId));
    };

    const movePlayerBySteps = (playerId: string, steps: number) => {
        updateGame((g) => g.moveBySteps(playerId, steps));
    };

    const answerQuestion = (playerId: string, steps: number, correct: boolean) => {
        updateGame((g) => g.answerQuestion(playerId, steps, correct));
    };

    return { game, startGame, movePlayerToTile, movePlayerBySteps, answerQuestion };
};

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
        newGame.initializePlayers();
        setGame(newGame);
    };

    const movePlayerToTile = (playerId: string, toTileId: string) => {
        if (!game) return;
        const updated = new Game({
            id: game.id,
            board: game.board,
            players: game.players,
        });
        updated.movePlayerTo(playerId, toTileId);
        setGame(updated);
    };

    const movePlayerBySteps = (playerId: string, steps: number) => {
        if (!game) return;
        const updated = new Game({
            id: game.id,
            board: game.board,
            players: game.players,
        });
        updated.movePlayerBy(playerId, steps);
        setGame(updated);
    };

    return { game, startGame, movePlayerToTile, movePlayerBySteps };
};

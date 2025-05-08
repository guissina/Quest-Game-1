import { useEffect, useState } from "react";
import { Player } from "../../models/Player";
import { GameEngine } from "../../services/game/GameEngine";

export const useTurnFlow = (engine: GameEngine | null) => {
    const [currentPlayer, setCurrentPlayer] = useState<Player | null>(null);

    useEffect(() => {
        if (!engine) {
            setCurrentPlayer(null);
            return;
        }
        setCurrentPlayer(engine.state.currentPlayer);
    }, [engine, engine?.state.aggregate.mustAnswerBeforeMoving]);

    return { currentPlayer };
};

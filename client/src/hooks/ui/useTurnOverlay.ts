import { useEffect, useState } from "react";
import { GameEngine } from "../../services/game/GameEngine";

export function useTurnOverlay(engine: GameEngine | null, delay = 2000) {
    const [showTurnOverlay, setShowTurnOverlay] = useState(false);
    const [showReadyPrompt, setShowReadyPrompt] = useState(false);

    useEffect(() => {
        if (!engine) return;
        const playerId = engine.state.currentPlayer.id;
        setShowTurnOverlay(true);

        const t1 = setTimeout(() => {
            setShowTurnOverlay(false);
            if (engine.state.aggregate.mustAnswerBeforeMoving[playerId])
                setShowReadyPrompt(true);
        }, delay);

        return () => clearTimeout(t1);
    }, [engine, engine?.state.currentPlayer?.id, delay]);

    return {
        showTurnOverlay,
        showReadyPrompt,
        hideReadyPrompt: () => setShowReadyPrompt(false),
    };
}

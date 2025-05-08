import { useState } from "react";
import { Board } from "../models/Board";
import { Player } from "../models/Player";
import { Game } from "../models/Game";
import { GameEngine } from "../services/game/GameEngine";
import { TurnManager } from "../services/game/TurnManager";
import { BoardManager } from "../services/game/BoardManager";
import { QuestionService } from "../services/game/QuestionService";
import { useTurnFlow } from "./flow/useTurnFlow";
import { useQuestionFlow } from "./flow/useQuestionFlow";
import { useBoardFlow } from "./flow/useBoardFlow";

export const useGame = () => {
    const [engine, setEngine] = useState<GameEngine | null>(null);

    const { currentPlayer } = useTurnFlow(engine);
    const { currentQuestion, pendingSteps, prepareQuestion, submitAnswer, revealPendingQuestion } = useQuestionFlow(engine);
    const { moveBySteps } = useBoardFlow(engine, prepareQuestion);

    const startGame = (board: Board, players: Player[]) => {
        const aggregate = new Game({ id: crypto.randomUUID(), board, players });
        const eng = new GameEngine(
            aggregate,
            new TurnManager(),
            new BoardManager(),
            new QuestionService()
        );
        eng.seed();
        setEngine(eng);
    };

    return {
        engine,
        currentPlayer,
        currentQuestion,
        pendingSteps,
        startGame,
        moveBySteps,
        submitAnswer,
        revealPendingQuestion,
    };
};

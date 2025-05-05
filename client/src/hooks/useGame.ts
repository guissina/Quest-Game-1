import { useState } from "react";
import { Board } from "../models/Board";
import { Player } from "../models/Player";
import { Question } from "../models/Question";
import { GameEngine } from "../services/game/GameEngine";
import { Game } from "../models/Game";
import { TurnManager } from "../services/game/TurnManager";
import { BoardManager } from "../services/game/BoardManager";
import { QuestionService } from "../services/game/QuestionService";

export const useGame = () => {
    const [engine, setEngine] = useState<GameEngine | null>(null);
    const [currentQuestion, setCurrentQuestion] = useState<Question | null>(null);
    const [pendingSteps, setPendingSteps] = useState<number>(0);

    const startGame = (board: Board, players: Player[]) => {
        const aggregate = new Game({ id: crypto.randomUUID(), board, players });
        const turnMgr = new TurnManager();
        const boardMgr = new BoardManager();
        const questionSvc = new QuestionService();
        const eng = new GameEngine(aggregate, turnMgr, boardMgr, questionSvc);

        eng.seed();
        setEngine(eng);
        setCurrentQuestion(null);
        setPendingSteps(0);
    };

    const updateEngine = (fn: (e: GameEngine) => void) => {
        if (!engine) return;
        const cloned = engine.clone();
        fn(cloned);
        setEngine(cloned);
    };

    const moveBySteps = (steps: number) => {
        if (!engine) return;
        const playerId = engine.state.currentPlayer.id;

        const tiles = engine.state.aggregate.board.tiles;
        const origin = tiles.find((t) =>
            t.players.some((p) => p.id === playerId)
        )!;
        const originIndex = tiles.indexOf(origin);
        const dest = tiles[(originIndex + steps) % tiles.length];
        
        updateEngine((e) => {
            e.move(playerId, dest.id);
        });

        if (dest.questionTheme?.questions.length) {
            setPendingSteps(steps);
            setCurrentQuestion(dest.questionTheme.questions[0]);
        }
    };

    const submitAnswer = (answer: string): boolean => {
        if (!engine || !currentQuestion) return false;
        const playerId = engine.state.currentPlayer.id;
        const correct =
            answer.trim().toLowerCase() ===
            currentQuestion.answer.trim().toLowerCase();
    
        updateEngine((e) => e.answer(playerId, pendingSteps, correct));
        setCurrentQuestion(null);
        setPendingSteps(0);
        return correct;
    };
  

    return {
        engine,
        currentQuestion,
        pendingSteps,
        startGame,
        moveBySteps,
        submitAnswer,
    };
};

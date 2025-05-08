import { useEffect, useState } from "react";
import { GameEngine } from "../../services/game/GameEngine";
import { Question } from "../../models/Question";

export const useQuestionFlow = (engine: GameEngine | null) => {
    const [currentQuestion, setCurrentQuestion] = useState<Question | null>(null);
    const [pendingSteps, setPendingSteps] = useState(0);

    useEffect(() => {
        if (!engine || currentQuestion) return;
        const playerId = engine.state.currentPlayer.id;

        if (engine.state.aggregate.mustAnswerBeforeMoving[playerId]) {
            const tile = engine.state.aggregate.board.tiles.find((t) =>
                t.players.some((p) => p.id === playerId)
            )!;
            setPendingSteps(0);
            setCurrentQuestion(tile.questionTheme!.questions[0]);
        }
    }, [engine, engine?.state.aggregate.mustAnswerBeforeMoving]);

    const prepareQuestion = (question: Question, steps: number) => {
        setPendingSteps(steps);
        setCurrentQuestion(question);
    };

    const submitAnswer = (answer: string): boolean => {
        if (!engine || !currentQuestion) return false;
        const playerId = engine.state.currentPlayer.id;
        const correct =
            answer.trim().toLowerCase() ===
            currentQuestion.answer.trim().toLowerCase();

        engine.answer(playerId, pendingSteps, correct);
        setCurrentQuestion(null);
        setPendingSteps(0);
        return correct;
    };

    const revealPendingQuestion = () => {
        if (!engine) return;
        const playerId = engine.state.currentPlayer.id;

        const tile = engine.state.aggregate.board.tiles.find(t =>
            t.players.some(p => p.id === playerId)
        );
        if (!tile?.questionTheme) return;

        setPendingSteps(0);
        setCurrentQuestion(tile.questionTheme.questions[0]);
    };

    return { currentQuestion, pendingSteps, prepareQuestion, submitAnswer, revealPendingQuestion };
};

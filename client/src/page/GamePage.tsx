import { useState } from "react";
import { useBoard } from "../hooks/useBoard";
import { useGame } from "../hooks/useGame";
import { getBoardById } from "../mocks/boardService.mock";
import BoardView from "../components/BoardView";
import QuestionModal from "../components/QuestionModal";
import { BoardSelector } from "../components/BoardSelector";
import { TurnIndicator } from "../components/TurnIndicator";
import { TokenSelector } from "../components/TokenSelector";
import { FeedbackMessage } from "../components/FeedbackMessage";
import { Player, PlayerProps } from "../models/Player";
import "./GamePage.scss";

interface Feedback {
    correct: boolean;
    correctAnswer: string;
}

export default function GamePage() {
    const { boards, loading, error } = useBoard();
    const {
        engine,
        currentQuestion,
        pendingSteps,
        startGame,
        moveBySteps,
        submitAnswer,
    } = useGame();

    const [selectedId, setSelectedId] = useState<string>("");
    const [feedback, setFeedback] = useState<Feedback | null>(null);

    const handleSelect = async (id: string) => {
        setSelectedId(id);
        if (!id) return;
        const board = await getBoardById(id);
        const players = ["Alice", "Bob"].map(
            (name, i) => new Player({ id: `${i + 1}`, name } as PlayerProps)
        );
        startGame(board, players);
    };

    const handleAnswer = (answer: string) => {
        if (!currentQuestion) return;
        const correct = submitAnswer(answer);
        setFeedback({ correct, correctAnswer: currentQuestion.answer });
        setTimeout(() => setFeedback(null), 3000);
    };

    if (loading) return <p className='gp-loading'>Loading boards…</p>;
    if (error) return <p className='gp-error'>{error}</p>;

    return (
        <div className='gp-container'>
            <h1 className='gp-title'>Board Game</h1>

            {!engine && (
                <BoardSelector
                    boards={boards}
                    selected={selectedId}
                    onSelect={handleSelect}
                />
            )}

            {engine && (
                <>
                    <TurnIndicator
                        playerName={engine.state.currentPlayer.name}
                    />

                    {currentQuestion && (
                        <QuestionModal
                            question={currentQuestion}
                            onSubmit={handleAnswer}
                            onCancel={() => submitAnswer("")}
                        />
                    )}

                    {feedback && (
                        <FeedbackMessage
                            correct={feedback.correct}
                            correctAnswer={feedback.correctAnswer}
                        />
                    )}

                    {!currentQuestion && (
                        <TokenSelector
                            tokens={engine.state.currentPlayer.movementTokens}
                            onMove={moveBySteps}
                        />
                    )}

                    <BoardView
                        board={engine.state.aggregate.board}
                        onTileClick={() => moveBySteps(pendingSteps || 1)}
                    />
                </>
            )}
        </div>
    );
}

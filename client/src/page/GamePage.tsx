import { useState } from "react";
import { useGame } from "../hooks/useGame";
import { useTurnOverlay } from "../hooks/ui/useTurnOverlay";
import { BoardSelector } from "../components/Board/BoardSelector";
import { TurnIndicator } from "../components/Player/TurnIndicator";
import { TokenSelector } from "../components/Player/TokenSelector";
import { BoardView } from "../components/Board/BoardView";
import { QuestionModal } from "../components/Question/QuestionModal";
import { FeedbackMessage } from "../components/Question/FeedbackMessage";
import "./GamePage.scss";
import { Board } from "../models/Board";
import { Player, PlayerProps } from "../models/Player";

interface FeedbackProps {
    correct: boolean;
    correctAnswer: string;
}

export default function GamePage() {
    const { engine, currentQuestion, pendingSteps, moveBySteps, submitAnswer, startGame, revealPendingQuestion } = useGame();
    const { showTurnOverlay, showReadyPrompt, hideReadyPrompt } = useTurnOverlay(engine);

    const [feedback, setFeedback] = useState<FeedbackProps | null>(null);
    const [hasSelectedBoard, setHasSelectedBoard] = useState(false);

    const handleBoardSelected = (board: Board) => {
        const players = ["Alice", "Bob"].map(
            (name, idx) => new Player({ id: `${idx+1}`, name } as PlayerProps)
        );
        startGame(board, players);
        setHasSelectedBoard(true);
    };

    const handleAnswer = (answer: string) => {
        if (!currentQuestion || !engine) return;
        const correct = submitAnswer(answer);
        setFeedback({ correct, correctAnswer: currentQuestion.answer });
        setTimeout(() => setFeedback(null), 3000);
    };

    if (!hasSelectedBoard || !engine)
        return <BoardSelector onSelectBoard={handleBoardSelected} />;

    return (
        <div className='gp-container'>
            <h1 className='gp-title'>Board Game</h1>

            <TurnIndicator playerName={engine.state.currentPlayer.name} />

            <div className='gp-content'>
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
            </div>

            {showTurnOverlay && (
                <div className='overlay turn-overlay'>
                    <div className='overlay-box'>
                        <h2>It’s {engine.state.currentPlayer.name}’s turn</h2>
                    </div>
                </div>
            )}

            {showReadyPrompt && (
                <div className='overlay ready-overlay'>
                    <div className='overlay-box'>
                        <p>Ready to answer your pending question?</p>
                        <button
                            onClick={() => {
                                revealPendingQuestion();  
                                hideReadyPrompt();        
                            }}
                        >
                            Yes
                        </button>
                    </div>
                </div>
            )}

            {currentQuestion && !showTurnOverlay && !showReadyPrompt && (
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
        </div>
    );
}

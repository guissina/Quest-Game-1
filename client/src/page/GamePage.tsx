import { ChangeEvent, useState } from "react";
import { useBoard } from "../hooks/useBoard";
import { useGame } from "../hooks/useGame";
import { getBoardById } from "../mocks/boardService.mock";
import BoardView from "../components/BoardView";
import QuestionModal from "../components/QuestionModal";
import { Player, PlayerProps } from "../models/Player";
import "./GamePage.scss";

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
    const [selectedId, setSelectedId] = useState("");

    const handleSelect = async (e: ChangeEvent<HTMLSelectElement>) => {
        const id = e.target.value;
        setSelectedId(id);
        if (!id) return;
        const board = await getBoardById(id);
        const players = ["Alice", "Bob", "Carol", "Dave"].map(
            (name, i) => new Player({ id: `${i}`, name } as PlayerProps)
        );
        startGame(board, players);
    };

    const [answerFeedback, setAnswerFeedback] = useState<null | boolean>(null);
    const handleAnswer = (answer: string) => {
        const correct = submitAnswer(answer);
        setAnswerFeedback(correct);
        setTimeout(() => setAnswerFeedback(null), 3000);
    };

    if (loading) return <p className='gp-loading'>Loading boards…</p>;
    if (error) return <p className='gp-error'>{error}</p>;

    return (
        <div className='gp-container'>
            <h1 className='gp-title'>Board Game</h1>

            {!engine && (
                <div className='gp-select-area'>
                    <label htmlFor='board-select' className='gp-label'>
                        Select board:
                    </label>
                    <select
                        id='board-select'
                        value={selectedId}
                        onChange={handleSelect}
                        className='gp-select'
                    >
                        <option value=''>-- choose --</option>
                        {boards.map((b) => (
                            <option key={b.id} value={b.id}>
                                {b.id}
                            </option>
                        ))}
                    </select>
                </div>
            )}

            {engine && (
                <>
                    <div className='gp-turn'>
                        <strong>Turn:&nbsp;</strong>
                        <span>{engine.state.currentPlayer.name}</span>
                    </div>

                    {currentQuestion && (
                        <QuestionModal
                            question={currentQuestion}
                            onSubmit={handleAnswer}
                            onCancel={() => submitAnswer("")}
                        />
                    )}

                    {answerFeedback !== null && (
                        <div
                            className={
                                answerFeedback
                                ? "gp-feedback gp-feedback-correct"
                                : "gp-feedback gp-feedback-wrong"
                            }
                        >
                            {answerFeedback
                                ? "🎉 Você acertou!"
                                : `❌ Errou! A resposta certa era "${currentQuestion?.answer}".`}
                        </div>
                    )}

                    {!currentQuestion && (
                        <div className="gp-tokens">
                            <p>Select movement token:</p>
                            <div className="gp-token-list">
                                {engine.state.currentPlayer.movementTokens.map((steps) => (
                                    <button
                                        key={steps}
                                        className="gp-token"
                                        onClick={() => moveBySteps(steps)}
                                    >
                                        {steps}
                                    </button>
                                ))}
                            </div>
                        </div>
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

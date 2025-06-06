import { useGameWebSocket } from "../hooks/useGameWebSocket";
import { TurnIndicator } from "../components/Player/TurnIndicator";
import { TokenSelector } from "../components/Player/TokenSelector";
import { BoardView } from "../components/Board/BoardView";
import "./GamePage.scss";
import { Player } from "../models/Player";
import { useEffect, useState } from "react";
import { QuestionModal } from "../components/Question/QuestionModal";
import { AbilityType } from "../models/PlayerState";
import AbilityPanel from "../components/Player/AbilityPanel";

interface GamePageProps {
    sessionId: string;
    myPlayerId: number;
    players: Player[];
}

export default function GamePage({ sessionId, myPlayerId, players }: GamePageProps) {
    const { gameState, drawQuestion, answerQuestion, useAbility } = useGameWebSocket(sessionId);

    const [questionOpen, setQuestionOpen] = useState(false);

    console.log('myPlayerId:', myPlayerId, 'type:', typeof myPlayerId);
    const myState = gameState?.playerStates.find((ps) => ps.playerId === myPlayerId);
    const currentState = gameState?.playerStates.find((ps) => ps.isCurrentTurn);

    useEffect(() => {
        if (gameState?.finished) {
            const winner = players.find((p) => p.id === gameState.winnerId);
            if (winner)
                alert(`Game finished! Winner: ${winner.name}`);
        }
    }, [gameState]);

    useEffect(() => {
        if (gameState && currentState && gameState.playerStates.some((ps) => ps.pendingQuestion))
            setQuestionOpen(true);
    }, [gameState]);

    if (!gameState)
        return <div className="gp-container"><p>Loading game state…</p></div>

    if (!myState)
        return <div className="gp-container"><p>Waiting for game to start…</p></div>

    const currentPlayer = players.find((p) => p.id === currentState?.playerId);

    const handleUseAbility = (ability: AbilityType) => {
        if (!myState) return;

        useAbility(myPlayerId, ability);
    }

    const handleConfirmMove = (steps: number) => {
        drawQuestion(myPlayerId, /* tema */ 1, steps);
    };

    const handleAnswer = (optionId: number) => {
        if (!myState.pendingQuestion) return;
        answerQuestion(myPlayerId, myState.pendingQuestion.id, optionId);
        setQuestionOpen(false);
    };

    const pendingQuestion = gameState.playerStates.find((ps) => ps.pendingQuestion)?.pendingQuestion;
    const canAnswer = currentState?.playerId === myPlayerId;

    return (
        <div className='gp-container'>
            <h1 className='gp-title'>Board Game</h1>

            {currentPlayer && <TurnIndicator playerName={currentPlayer.name} />}

            <div className='gp-content'>
                {pendingQuestion && (
                    <QuestionModal
                        isOpen={questionOpen}
                        question={pendingQuestion}
                        canAnswer={canAnswer}
                        onRequestClose={() => setQuestionOpen(false)}
                        onAnswer={handleAnswer}
                    />
                )}

                {currentState?.playerId === myPlayerId &&
                    !myState.pendingQuestion && (
                        <TokenSelector
                            tokens={myState.tokens}
                            disabled={false}
                            onConfirm={handleConfirmMove}
                        />
                    )}
                {myState && currentState?.playerId === myPlayerId && !myState.pendingQuestion && (
                    <AbilityPanel
                        playerState={myState}
                        onUseAbility={handleUseAbility}
                        disabled={false}
                    />
                )}

                <BoardView
                    board={gameState.board}
                    playerStates={gameState.playerStates}
                />
            </div>
        </div>
    );
}

import { useGameWebSocket } from "../hooks/useGameWebSocket";
import { TurnIndicator } from "../components/Player/TurnIndicator";
import { TokenSelector } from "../components/Player/TokenSelector";
import { BoardView } from "../components/Board/BoardView";
import "./GamePage.scss";
import { Player } from "../models/Player";

interface GamePageProps {
    sessionId: string;
    players: Player[];
}

export default function GamePage({ sessionId, players }: GamePageProps) {
    const { gameState, movePlayer, answerQuestion } = useGameWebSocket(sessionId);

    if (!gameState) {
        return (
            <div className='gp-container'>
                <p>Loading game state…</p>
            </div>
        );
    }

    const currentPlayer = gameState.playerStates.find((ps) => ps.isCurrentTurn)!;
    const currentPlayerId = currentPlayer?.playerId;

    return (
        <div className='gp-container'>
            <h1 className='gp-title'>Board Game</h1>

            <TurnIndicator playerName={currentPlayer.playerId} />

            <div className='gp-content'>
                <TokenSelector
                    tokens={Array.from(
                        { length: currentPlayer.tokens },
                        (_, i) => i + 1
                    )}
                    onMove={(steps) => movePlayer(currentPlayerId, steps)}
                />

                <BoardView
                    board={gameState.board}
                    playerStates={gameState.playerStates}
                    onTileClick={(tileId) =>
                        movePlayer(currentPlayer.playerId, Number(tileId))
                    }
                />
            </div>
        </div>
    );
}

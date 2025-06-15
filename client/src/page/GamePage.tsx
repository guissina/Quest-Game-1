import { useCallback, useEffect, useMemo, useState } from 'react';
import TurnIndicator from '../components/GameHeader/GameHeader';
import SidebarPlayers from '../components/SidebarPlayers/SidebarPlayers';
import SidebarActions from '../components/SidebarActions/SidebarActions';
import { QuestionModal } from '../components/Question/QuestionModal';
import BoardView from '../components/BoardView/BoardView';
import { Player } from '../models/Player';
import { AbilityType } from '../models/PlayerState';
import { useGameWebSocket } from '../hooks/useGameWebSocket';
import styles from './GamePage.module.scss';

interface GamePageProps {
  sessionId: string;
  myPlayerId: number;
  players: Player[];
}

export default function GamePage({ sessionId, myPlayerId, players }: GamePageProps) {
  const { gameState, drawQuestion, answerQuestion, useAbility } = useGameWebSocket(sessionId);
  const [questionOpen, setQuestionOpen] = useState(false);

  const playerStates = gameState?.playerStates ?? [];

  const myState = useMemo(
    () => playerStates.find(ps => ps.playerId === myPlayerId),
    [playerStates, myPlayerId]
  );
  const currentState = useMemo(
    () => playerStates.find(ps => ps.isCurrentTurn),
    [playerStates]
  );
  const currentPlayer = useMemo(
    () => players.find(p => p.id === currentState?.playerId),
    [players, currentState?.playerId]
  );
  const pendingQuestion = useMemo(
    () => myState?.pendingQuestion,
    [myState]
  );
  const canAnswer = currentState?.playerId === myPlayerId;

  const handleConfirmMove = useCallback((steps: number) => {
    const tiles = gameState?.board.tiles ?? [];
    const currentIndex = tiles.findIndex(t => t.id === myState?.tileId);
    const destination = tiles[currentIndex + steps];
    const themeId = destination.themes[0]?.id ?? 0;

    drawQuestion(myPlayerId, themeId, steps);
  }, [
    gameState,
    myState?.tileId,
    myPlayerId,
    drawQuestion
  ]);

  const handleUseAbility = useCallback((ability: AbilityType) => {
    useAbility(myPlayerId, ability);
  }, [useAbility, myPlayerId]);

  const handleAnswer = useCallback((optId: number) => {
    if (!pendingQuestion) return;

    answerQuestion(myPlayerId, pendingQuestion.id, optId);
    setQuestionOpen(false);
  }, [answerQuestion, myPlayerId, pendingQuestion]);

  useEffect(() => {
    if (gameState?.finished) {
      const winner = players.find(p => p.id === gameState.winnerId);
      winner && alert(`🏆 Game finished! Winner: ${winner.name}`);
    }
  }, [gameState, players]);

  useEffect(() => {
    if (pendingQuestion) setQuestionOpen(true);
  }, [pendingQuestion]);

  if (!myState)
    return <div className={styles.loading}>Waiting for game to start…</div>;
  if (!gameState)
    return <div className={styles.loading}>Loading game…</div>;

  return (
    <div className={styles.container}>
      <TurnIndicator currentPlayerName={currentPlayer?.name} />

      <div className={styles.main}>
        <SidebarPlayers
          players={players}
          playerStates={gameState.playerStates}
        />

        <BoardView
          board={gameState.board}
          playerStates={gameState.playerStates}
          players={players}
        />

        <SidebarActions
          myPlayerId={myPlayerId}
          myState={myState}
          currentPlayerId={currentState?.playerId}
          onConfirmMove={handleConfirmMove}
          onUseAbility={handleUseAbility}
        />
      </div>

      {pendingQuestion && (
        <QuestionModal
          isOpen={questionOpen}
          question={pendingQuestion}
          canAnswer={canAnswer}
          onRequestClose={() => setQuestionOpen(false)}
          onAnswer={handleAnswer}
        />
      )}
    </div>
  );
}

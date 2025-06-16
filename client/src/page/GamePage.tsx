import { useCallback, useEffect, useMemo, useState } from 'react';
import SidebarPlayers from '../components/SidebarPlayers/SidebarPlayers';
import QuestionModal from '../components/Question/QuestionModal';
import BoardView from '../components/BoardView/BoardView';
import { Player } from '../models/Player';
import { AbilityType } from '../models/PlayerState';
import { useGameWebSocket } from '../hooks/useGameWebSocket';
import styles from './GamePage.module.scss';
import PlayerCard from '../components/PlayerCard/PlayerCard';

interface GamePageProps {
  sessionId: string;
  myPlayerId: number;
  players: Player[];
}

export default function GamePage({
  sessionId,
  myPlayerId,
  players,
}: GamePageProps) {
  const { gameState, drawQuestion, answerQuestion, useAbility } =
    useGameWebSocket(sessionId);
  const [questionOpen, setQuestionOpen] = useState(false);
  const { player, oponents } = players.reduce<{
    player: Player | null;
    oponents: Player[];
  }>(
    (acc, current) => {
      if (current.id === myPlayerId) {
        acc.player = current;
      } else {
        acc.oponents.push(current);
      }
      return acc;
    },
    {
      player: null,
      oponents: [],
    },
  );

  const playerStates = gameState?.playerStates ?? [];

  const myState = useMemo(
    () => playerStates.find((ps) => ps.playerId === myPlayerId),
    [playerStates, myPlayerId],
  );
  const currentState = useMemo(
    () => playerStates.find((ps) => ps.isCurrentTurn),
    [playerStates],
  );
  const currentPlayer = useMemo(
    () => players.find((p) => p.id === currentState?.playerId),
    [players, currentState?.playerId],
  );

  const pendingQuestion = currentState?.pendingQuestion ?? null;
  const canAnswer = currentState?.playerId === myPlayerId;

  const handleConfirmMove = useCallback(
    (steps: number) => {
      const tiles = gameState?.board.tiles ?? [];
      const currentIndex = tiles.findIndex((t) => t.id === myState?.tileId);
      const destination = tiles[currentIndex + steps];
      const themeId = destination.themes[0]?.id ?? 0;

      drawQuestion(myPlayerId, themeId, steps);
    },
    [gameState, myState?.tileId, myPlayerId, drawQuestion],
  );

  const handleUseAbility = useCallback(
    (ability: AbilityType) => {
      useAbility(myPlayerId, ability);
    },
    [useAbility, myPlayerId],
  );

  const handleAnswer = useCallback(
    (optId: number) => {
      if (!pendingQuestion || !canAnswer) return;

      answerQuestion(myPlayerId, pendingQuestion.id, optId);
      setQuestionOpen(false);
    },
    [answerQuestion, myPlayerId, pendingQuestion],
  );

  useEffect(() => {
    if (gameState?.finished) {
      const winner = players.find((p) => p.id === gameState.winnerId);
      winner && alert(`🏆 Game finished! Winner: ${winner.name}`);
    }
  }, [gameState, players]);

  useEffect(() => {
    if (pendingQuestion) setQuestionOpen(true);
  }, [pendingQuestion]);

  if (!myState)
    return <div className={styles.loading}>Waiting for game to start…</div>;
  if (!gameState) return <div className={styles.loading}>Loading game…</div>;

  return (
    <div className={styles.container}>
      {/* <TurnIndicator currentPlayerName={currentPlayer?.name} /> */}

      <div className={styles.main}>
        {/* <SidebarPlayers
          players={players}
          playerStates={gameState.playerStates}
          onConfirmMove={handleConfirmMove}
          onUseAbility={handleUseAbility}
        /> */}

        {player && (
          <PlayerCard
            key={player.id}
            player={player}
            showAbilities={true}
            state={playerStates.find((s) => s.playerId === player.id)!}
            onConfirmMove={handleConfirmMove}
            onUseAbility={handleUseAbility}
          />
        )}

        <BoardView
          board={gameState.board}
          playerStates={gameState.playerStates}
          players={players}
        />

        <SidebarPlayers
          players={oponents}
          playerStates={gameState.playerStates}
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

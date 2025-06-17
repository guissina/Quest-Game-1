import { useCallback, useEffect, useMemo, useState } from 'react';
import SidebarPlayers from '../components/SidebarPlayers/SidebarPlayers';
import QuestionModal from '../components/Question/QuestionModal';
import BoardView from '../components/BoardView/BoardView';
import { Player } from '../models/Player';
import { AbilityType } from '../models/PlayerState';
import { useGameWebSocket } from '../hooks/useGameWebSocket';
import styles from './GamePage.module.scss';
import PlayerCard from '../components/PlayerCard/PlayerCard';
import ConfettiOverlay from '../components/ConfettiOverlay/ConfettiOverlay';

interface GamePageProps {
  sessionId: string;
  myPlayerId: number;
  players: Player[];
  hostId: number;
  onFinished: () => void;
}

export default function GamePage({
  sessionId,
  myPlayerId,
  players,
  hostId,
  onFinished,
}: GamePageProps) {
  const { gameState, drawQuestion, answerQuestion, useAbility } =
    useGameWebSocket(sessionId);
  const [questionOpen, setQuestionOpen] = useState(false);
  const [confettiRunning, setConfettiRunning] = useState(false);
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

  const pendingQuestion = currentState?.pendingQuestion ?? null;
  const canAnswer = currentState?.playerId === myPlayerId;

  const handleConfirmMove = useCallback(
    (steps: number) => {
      const tiles = gameState?.board.tiles ?? [];
      const currentIndex = tiles.findIndex((t) => t.id === myState?.tileId);
      const themeId = tiles[currentIndex].themes[0]?.id ?? 0;

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
    if (gameState?.finished)
      setConfettiRunning(true);
  }, [gameState?.finished]);

  useEffect(() => {
    if (pendingQuestion) setQuestionOpen(true);
  }, [pendingQuestion]);

  const isHost = myPlayerId === hostId;
  const handleStopConfetti = useCallback(() => {
    if (!isHost) return;
    setConfettiRunning(false);
    onFinished();
  }, [isHost, onFinished]);

  if (!myState) return <div className={styles.loading}>Waiting for game to start…</div>;
  if (!gameState) return <div className={styles.loading}>Loading game…</div>;

  const winnerName = players.find((p) => p.id === gameState.winnerId)?.name ?? '—';

  return (
    <>
      <ConfettiOverlay
        winnerName={winnerName}
        running={confettiRunning}
        isHost={isHost}
        onStop={handleStopConfetti}
      />

      <div className={styles.container}>
        <div className={styles.main}>
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
    </>
  );
}

import { useEffect, useState } from "react";
import { useGameWebSocket } from "../hooks/useGameWebSocket";
import { QuestionModal } from "../components/Question/QuestionModal";
import "./GamePage.scss";
import { PlayerProps } from "../models/Player";
import { AbilityType } from "../models/PlayerState";
import image1 from '../assets/avatar/avatar1.png';
import Coins from "../components/Coins/Coins";

interface GamePageProps {
  sessionId: string;
  myPlayerId: number;
  players: PlayerProps[];
}

export default function GamePage({ sessionId, myPlayerId, players }: GamePageProps) {
  const { gameState, drawQuestion, answerQuestion, useAbility } = useGameWebSocket(sessionId);
  const [questionOpen, setQuestionOpen] = useState(false);

  const myState = gameState?.playerStates.find(ps => ps.playerId === myPlayerId);
  const currentState = gameState?.playerStates.find(ps => ps.isCurrentTurn);
  const currentPlayer = players.find(p => p.id === currentState?.playerId);
  const pendingQuestion = gameState?.playerStates.find(ps => ps.pendingQuestion)?.pendingQuestion;
  const canAnswer = currentState?.playerId === myPlayerId;

  useEffect(() => {
    if (gameState?.finished) {
      const winner = players.find(p => p.id === gameState.winnerId);
      if (winner) alert(`🏆 Game finished! Winner: ${winner.name}`);
    }
  }, [gameState]);

  useEffect(() => {
    if (pendingQuestion) setQuestionOpen(true);
  }, [pendingQuestion]);

  if (!gameState) return <div className="gp-loading">Loading game…</div>;
  if (!myState)   return <div className="gp-loading">Waiting for game to start…</div>;

  const handleConfirmMove = (steps: number) => {
    drawQuestion(myPlayerId, /* tema */ 2, steps);
  };

  const handleAnswer = (optId: number) => {
    if (!myState.pendingQuestion) return;
    answerQuestion(myPlayerId, myState.pendingQuestion.id, optId);
    setQuestionOpen(false);
  };

  const handleUseAbility = (ability: AbilityType) => {
    useAbility(myPlayerId, ability);
  };

  return (
    <div className="gp-container">
      {/* Turn Indicator */}
      <header className="gp-header">
        <div className="gp-turn-indicator">
          <strong>Turn:</strong> <span>{currentPlayer?.name}</span>
        </div>
      </header>

      <div className="gp-main">
        {/* Left Sidebar: Players */}
        <aside className="gp-sidebar gp-sidebar--players">
          {players.map(p => {
            const ps = gameState.playerStates.find(s => s.playerId === p.id)!;
            const isCurrent = ps.isCurrentTurn;
            return (
              <div key={p.id} className={`gp-player-card ${isCurrent ? 'current' : ''}`}>
                <img src={image1} alt={p.name} className="gp-player-avatar"/>
                <span className="gp-player-name">{p.name}</span>
                <div className="gp-player-tokens">
                  {ps.tokens.map((t, i) => (
                    <Coins key={i} value={t} />
                  ))}
                </div>
              </div>
            );
          })}
        </aside>

        {/* Center: Board */}
        <section
          className="gp-board"
          style={{
            '--cols': gameState.board.cols,
            '--rows': gameState.board.rows
          } as React.CSSProperties}
        >
          {gameState.board.tiles.map(tile => {
            const occupants = gameState.playerStates.filter(ps => ps.tileId === tile.id);
            return (
              <div
                key={tile.id}
                className="gp-tile"
                style={{
                  gridColumn: tile.col + 1,
                  gridRow: tile.row + 1,
                }}
              >
                <div className="gp-tile-id">{tile.sequence}</div>
                <div className="gp-tile-theme">{tile.themes[0]?.name}</div>
                <div className="gp-tile-players">
                  {occupants.map(o => {
                    const player = players.find(p => p.id === o.playerId);
                    return (
                      <img
                        key={o.playerId}
                        src={image1}
                        alt={player?.name}
                        className="gp-tile-avatar"
                        title={player?.name}
                      />
                    );
                  })}
                </div>
              </div>
            );
          })}
        </section>

        {/* Right Sidebar: Abilities & TokenSelector */}
        <aside className="gp-sidebar gp-sidebar--actions">
          {pendingQuestion && (
            <QuestionModal
              isOpen={questionOpen}
              question={pendingQuestion}
              canAnswer={canAnswer}
              onRequestClose={() => setQuestionOpen(false)}
              onAnswer={handleAnswer}
            />
          )}

          {/* Only if it's my turn and no question pending */}
          {currentState?.playerId === myPlayerId && !myState.pendingQuestion && (
            <>
              <div className="gp-section">
                <h3>Selecione movimento</h3>
                <div className="gp-move-tokens">
                  {myState.tokens.map((t) => (
                    <button
                      key={t}
                      className="secondary-btn gp-move-token"
                      onClick={() => handleConfirmMove(t)}
                    >
                      {t}
                    </button>
                  ))}
                </div>
              </div>

              <div className="gp-section">
                <h3>Habilidades</h3>
                <div className="gp-abilities">
                  {myState.getAllAbilities().map(ability => {
                    const active = myState.isAbilityActive(ability);
                    return (
                      <button
                        key={ability}
                        className={`gp-ability-btn ${active ? 'active' : 'inactive'}`}
                        onClick={() => handleUseAbility(ability)}
                        title={active ? 'Ativa' : 'Inativa'}
                      >
                        {ability}
                        <span className="status">{active ? '✓' : '○'}</span>
                      </button>
                    );
                  })}
                </div>
              </div>
            </>
          )}
        </aside>
      </div>
    </div>
  );
}

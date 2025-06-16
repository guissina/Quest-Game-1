import React from 'react';
import { Board } from '../../models/Board';
import { PlayerProps } from '../../models/Player';
import { PlayerState } from '../../models/PlayerState';
import styles from './BoardView.module.scss';
import { getAvatarUrl } from '../../utils/avatar';

interface BoardViewProps {
  board: Board;
  playerStates: PlayerState[];
  players: PlayerProps[];
}

export default function BoardView({
  board,
  playerStates,
  players,
}: BoardViewProps) {
  const temaClasses: Record<number, string> = {
    0: styles.tema0,
    1: styles.tema1,
    2: styles.tema2,
    3: styles.tema3,
    4: styles.tema4,
    5: styles.tema5,
    6: styles.tema6,
  };
  const temas: number[] = [];

  return (
    <section
      className={styles.board}
      style={
        {
          '--cols': board.cols,
          '--rows': board.rows,
        } as React.CSSProperties
      }
    >
      {board.tiles.map((tile) => {
        const { id, sequence, themes, col, row } = tile;
        const firstTheme = themes[0];
        let themeIndex = temas.indexOf(firstTheme.id);
        if (themeIndex === -1) {
          temas.push(firstTheme.id);
          themeIndex = temas.length - 1;
        }

        const occupants = playerStates
          .filter((ps) => ps.tileId === id)
          .map((ps) => players.find((p) => p.id === ps.playerId)!)
          .filter(Boolean);

        return (
          <div
            key={id}
            className={`${styles.tile} ${temaClasses[themeIndex]}`}
            style={{
              gridColumn: col + 1,
              gridRow: row + 1,
            }}
          >
            <div className={styles.tileId}>{sequence}</div>

            {firstTheme?.name && (
              <div className={styles.tileTheme}>{firstTheme.name}</div>
            )}

            <div className={styles.tilePlayers}>
              {occupants.map((player) => (
                <img
                  key={player.id}
                  src={getAvatarUrl(player.avatarIndex)}
                  alt={player.name}
                  title={player.name}
                  className={styles.tileAvatar}
                />
              ))}
            </div>
          </div>
        );
      })}
    </section>
  );
}

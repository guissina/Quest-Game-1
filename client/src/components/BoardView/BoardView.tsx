import React from 'react';
import { Board } from '../../models/Board';
import { PlayerProps } from '../../models/Player';
import { PlayerState } from '../../models/PlayerState';
import styles from './BoardView.module.scss';
import image1 from '../../assets/avatar/avatar1.png';

interface BoardViewProps {
    board: Board;
    playerStates: PlayerState[];
    players: PlayerProps[];
}

export default function BoardView({ board, playerStates, players }: BoardViewProps) {
    return (
        <section
            className={styles.board}
            style={{
                '--cols': board.cols,
                '--rows': board.rows,
            } as React.CSSProperties}
        >
            {board.tiles.map(tile => {
                const { id, sequence, themes, col, row } = tile;
                const themeName = themes[0]?.name;
                const occupants = playerStates
                    .filter(ps => ps.tileId === id)
                    .map(ps => players.find(p => p.id === ps.playerId)!)
                    .filter(Boolean);

                return (
                    <div
                        key={id}
                        className={styles.tile}
                        style={{
                        gridColumn: col + 1,
                        gridRow:    row + 1,
                        }}
                    >
                        <div className={styles.tileId}>{sequence}</div>

                        {themeName && <div className={styles.tileTheme}>{themeName}</div>}
                        
                        <div className={styles.tilePlayers}>
                            {occupants.map(player => (
                                <img
                                key={player.id}
                                src={image1}
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

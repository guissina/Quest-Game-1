import { Board } from "../../models/Board";
import { PlayerState } from "../../models/PlayerState";
import "./BoardView.scss";

interface BoardViewProps {
    board: Board;
    playerStates: PlayerState[];
}

export function BoardView({ board, playerStates }: BoardViewProps) {
    const { rows, cols, tiles, /*name*/ } = board;

    return (
        <div
            className='board-view'
            style={{
                gridTemplateColumns: `repeat(${cols}, 1fr)`,
                gridTemplateRows: `repeat(${rows}, 1fr)`,
            }}
        >
            {tiles.map((tile) => {
                const occupants = playerStates.filter((ps) => ps.tileId === tile.id);

                return (
                    <div
                        key={tile.id}
                        className='bv-tile'
                        style={{
                            gridColumn: tile.col + 1,
                            gridRow: tile.row + 1,
                        }}
                    >
                        <strong className='bv-tile-id'>{tile.id}</strong> 
                        <strong className='bv-tile-id'>{tile.theme?.name}</strong>

                        <div className='bv-players'>
                            {occupants.length === 0 ? (
                                <span className='bv-empty'></span>
                            ) : (
                                occupants
                                    .map((p) => `Player ${p.playerId}`)
                                    .join(", ")
                            )}
                        </div>
                    </div>
                );
            })}
        </div>
    );
}

import { Board } from "../../models/Board";
import "./BoardView.scss";

interface BoardViewProps {
    board: Board;
    onTileClick(tileId: string): void;
}

export function BoardView({ board, onTileClick }: BoardViewProps) {
    const { rows, cols, tiles } = board;

    return (
        <div
            className='board-view'
            style={{
                gridTemplateColumns: `repeat(${cols}, 1fr)`,
                gridTemplateRows: `repeat(${rows}, 1fr)`,
            }}
        >
            {tiles.map((tile) => (
                <div
                    key={tile.id}
                    className='bv-tile'
                    style={{
                        gridColumn: tile.col + 1,
                        gridRow: tile.row + 1,
                    }}
                    onClick={() => onTileClick(tile.id)}
                >
                    <strong className='bv-tile-id'>{tile.id}</strong>
                    {tile.questionTheme && (
                        <small className='bv-theme'>
                            {tile.questionTheme.name}
                        </small>
                    )}
                    {tile.specialCard && (
                        <em className='bv-special'>{tile.specialCard.name}</em>
                    )}
                    <div className='bv-players'>
                        {tile.players.length === 0 ? (
                            <span className='bv-empty'></span>
                        ) : (
                            tile.players.map((p) => p.name).join(", ")
                        )}
                    </div>
                </div>
            ))}
        </div>
    );
}

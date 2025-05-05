import { Board } from "../models/Board";
import { Tile as TileModel } from "../models/Tile";
import "./BoardView.scss";

interface BoardViewProps {
    board: Board;
    onTileClick: (tileId: string) => void;
}

export default function BoardView({ board, onTileClick }: BoardViewProps) {
    const total = board.tiles.length;
    const cols = Math.ceil(Math.sqrt(total));

    return (
        <div
            className='bv-container'
            style={{ gridTemplateColumns: `repeat(${cols}, 1fr)` }}
        >
            {board.tiles.map((tile: TileModel) => (
                <div
                    key={tile.id}
                    onClick={() => onTileClick(tile.id)}
                    className='bv-tile'
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
                            <span className='bv-empty'>– vazio –</span>
                        ) : (
                            tile.players.map((p) => p.name).join(", ")
                        )}
                    </div>
                </div>
            ))}
        </div>
    );
}

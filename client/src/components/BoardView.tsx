import { Board } from "../models/Board";
import { Tile as TileModel } from "../models/Tile";

interface BoardViewProps {
    board: Board;
    onTileClick: (tileId: string) => void;
}

export default function BoardView({ board, onTileClick }: BoardViewProps) {
    const total = board.tiles.length;
    const cols = Math.ceil(Math.sqrt(total));

    return (
        <div
            className='board-grid'
            style={{
                display: "grid",
                gridTemplateColumns: `repeat(${cols}, 1fr)`,
                gap: 8,
                padding: 16,
                backgroundColor: "pink",
                borderRadius: 8,
            }}
        >
            {board.tiles.map((tile: TileModel) => (
                <div
                    key={tile.id}
                    className='tile-card'
                    onClick={() => onTileClick(tile.id)}
                    style={{
                        cursor: "pointer",
                        display: "flex",
                        flexDirection: "column",
                        justifyContent: "space-between",
                        alignItems: "center",
                        padding: 8,
                        backgroundColor: "black",
                        border: "1px solid #333",
                        borderRadius: 6,
                        minHeight: 80,
                    }}
                >
                    <strong>{tile.id}</strong>
                    <small>Tema: {tile.questionTheme.name}</small>
                    {tile.specialCard && <em>{tile.specialCard.name}</em>}
                    <div style={{ fontSize: 12 }}>
                        {tile.players.length === 0 ? (
                            <span style={{ fontStyle: "italic" }}>
                                – vazio –
                            </span>
                        ) : (
                            tile.players.map((p) => p.name).join(", ")
                        )}
                    </div>
                </div>
            ))}
        </div>
    );
}

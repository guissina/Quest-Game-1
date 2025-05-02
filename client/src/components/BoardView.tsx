import { Board } from "../models/Board";
import { Tile as TileModel } from "../models/Tile";

interface BoardViewProps {
    board: Board;
}

export default function BoardView({ board }: BoardViewProps) {
    const total = board.tiles.length;
    const cols = Math.ceil(Math.sqrt(total));

    return (
        <div
            className='board-grid'
            style={{
                display: "grid",
                gridTemplateColumns: `repeat(${cols}, 1fr)`,
                gap: "8px",
                padding: "16px",
                backgroundColor: "#f0f0f0",
                borderRadius: "8px",
            }}
        >
            {board.tiles.map((tile: TileModel, idx: number) => (
                <div
                    key={tile.id}
                    className='tile-card'
                    style={{
                        display: "flex",
                        flexDirection: "column",
                        justifyContent: "space-around",
                        alignItems: "center",
                        padding: "8px",
                        backgroundColor: "Black",
                        border: "1px solid #ccc",
                        borderRadius: "6px",
                        boxShadow: "0 1px 3px rgba(0,0,0,0.1)",
                    }}
                >
                    <div>
                        <strong>{tile.id}</strong>
                    </div>
                    <div>Tema: {tile.questionTheme}</div>
                    <div>
                        {tile.players.length === 0 ? (
                            <span style={{ fontStyle: "italic" }}>
                                – vazio –
                            </span>
                        ) : (
                            tile.players.map((p, i) => (
                                <div key={i}>
                                    {"ID: "}{p.id}
                                    {p.name} ({p.score} pts) {p.movementTokens.length}{" Tokens de movimento: "}
                                    {p.movementTokens.filter(mt => !mt.isLost).map((mt) => (
                                        <span key={mt.id}>
                                            {mt.value}{" "}
                                            {mt.isLost ? "true" : ""}
                                        </span>
                                    ))}
                                </div>
                            ))
                        )}
                    </div>
                </div>
            ))}
        </div>
    );
}

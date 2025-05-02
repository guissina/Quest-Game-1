import { ChangeEvent, useState } from "react";
import { useBoard } from "../hooks/useBoard";
import BoardView from "../components/BoardView";
import { Board as BoardModel } from "../models/Board";

export default function GamePage() {
    const { boards, selectedBoard, loading, error, fetchBoardById } =
        useBoard();

    const [selectedId, setSelectedId] = useState<string>("");

    const handleChange = (e: ChangeEvent<HTMLSelectElement>) => {
        const id = e.target.value;
        setSelectedId(id);
        if (id) fetchBoardById(id);
    };

    return (
        <div style={{ padding: "16px" }}>
            <h1>Jogo de Tabuleiro</h1>

            <label htmlFor='board-select' style={{ marginRight: "8px" }}>
                Selecione o tabuleiro:
            </label>
            <select
                id='board-select'
                onChange={handleChange}
                value={selectedId}
                disabled={loading}
                style={{ padding: "4px 8px" }}
            >
                <option value=''>-- escolha --</option>
                {boards.map((b: BoardModel) => (
                    <option key={b.id} value={b.id}>
                        {b.id}
                    </option>
                ))}
            </select>

            {loading && <p>Carregando...</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}

            {selectedBoard && !loading && !error && (
                <div style={{ marginTop: "24px" }}>
                    <BoardView board={selectedBoard} />
                </div>
            )}
        </div>
    );
}

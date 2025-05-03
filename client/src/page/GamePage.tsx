import { ChangeEvent, useState } from "react";
import { useBoard } from "../hooks/useBoard";
import { useGame } from "../hooks/useGame";
//import { getBoardById } from "../services/boardService";
import { getBoardById } from "../mocks/boardService.mock";
import BoardView from "../components/BoardView";
import { Player, PlayerProps } from "../models/Player";

export default function GamePage() {
    
    const { boards, loading: listLoading, error: listError } = useBoard();
    const { game, startGame, movePlayerToTile, movePlayerBySteps } = useGame();
    const [selectedId, setSelectedId] = useState<string>("");

    const handleChange = async (e: ChangeEvent<HTMLSelectElement>) => {
        const id = e.target.value;
        setSelectedId(id);
        if (!id) return;

        try {
            const board = await getBoardById(id);
            // TODO Substituir mocks de players
            const players: Player[] = [
                new Player({ id: "0", name: "Alice" } as PlayerProps),
                new Player({ id: "1", name: "Bob" } as PlayerProps),
                new Player({ id: "2", name: "Carol" } as PlayerProps),
                new Player({ id: "3", name: "Dave" } as PlayerProps),
            ];
            startGame(board, players);
        } catch (err: any) {
            console.error("Erro ao carregar board:", err);
        }
    };

    return (
        <div style={{ padding: "16px" }}>
            <h1>Jogo de Tabuleiro</h1>

            <label htmlFor='board-select' style={{ marginRight: 8 }}>
                Selecione o tabuleiro:
            </label>
            <select
                id='board-select'
                value={selectedId}
                onChange={handleChange}
                disabled={listLoading}
                style={{ padding: "4px 8px" }}
            >
                <option value=''>-- escolha --</option>
                {boards.map((b) => (
                    <option key={b.id} value={b.id}>
                        {b.id}
                    </option>
                ))}
            </select>

            {listLoading && <p>Carregando tabuleiros…</p>}
            {listError && <p style={{ color: "red" }}>Erro: {listError}</p>}

            {game && (
                <div style={{ marginTop: 24 }}>
                    <h2>Game ID: {game.id}</h2>
                    <BoardView board={game.board} movePlayerToTile={movePlayerToTile} movePlayerBySteps={movePlayerBySteps} />
                </div>
            )}
        </div>
    );
}

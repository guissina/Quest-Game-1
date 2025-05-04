// src/pages/GamePage.tsx
import { ChangeEvent, useState } from "react";
import { useBoard } from "../hooks/useBoard";
import { useGame } from "../hooks/useGame";
import { getBoardById } from "../mocks/boardService.mock";
import BoardView from "../components/BoardView";
import { Player, PlayerProps } from "../models/Player";

export default function GamePage() {
    const { boards, loading, error } = useBoard();
    const {
        game,
        startGame,
        movePlayerToTile,
        movePlayerBySteps,
        answerQuestion,
    } = useGame();

    const [selectedId, setSelectedId] = useState("");

    const handleSelect = async (e: ChangeEvent<HTMLSelectElement>) => {
        const id = e.target.value;
        setSelectedId(id);
        if (!id) return;

        const board = await getBoardById(id);
        const players: Player[] = [
            new Player({ id: "0", name: "Alice" } as PlayerProps),
            new Player({ id: "1", name: "Bob" } as PlayerProps),
            new Player({ id: "2", name: "Carol" } as PlayerProps),
            new Player({ id: "3", name: "Dave" } as PlayerProps),
        ];
        startGame(board, players);
    };

    if (loading) return <p>Carregando tabuleiros…</p>;
    if (error) return <p style={{ color: "red" }}>Erro: {error}</p>;

    return (
        <div style={{ padding: 16 }}>
            <h1>Jogo de Tabuleiro</h1>

            {!game && (
                <>
                    <label htmlFor='board-select'>Escolha o tabuleiro:</label>
                    <select
                        id='board-select'
                        value={selectedId}
                        onChange={handleSelect}
                        style={{ marginLeft: 8, padding: "4px 8px" }}
                    >
                        <option value=''>-- selecione --</option>
                        {boards.map((b) => (
                            <option key={b.id} value={b.id}>
                                {b.id}
                            </option>
                        ))}
                    </select>
                </>
            )}

            {game && (
                <>
                    <div style={{ margin: "16px 0" }}>
                        <strong>Turno de:</strong>{" "}
                        <span style={{ fontSize: 18 }}>
                            {game.currentPlayer.name}
                        </span>
                    </div>

                    <div style={{ marginBottom: 16 }}>
                        <strong>Tokens disponíveis:</strong>{" "}
                        {game.currentPlayer.movementTokens.join(", ")}
                    </div>

                    <div style={{ marginBottom: 16 }}>
                        <button
                            onClick={() =>
                                answerQuestion(game.currentPlayer.id, 2, true)
                            }
                            style={{ marginRight: 8, padding: "8px 12px" }}
                        >
                            Answer Correct (move 2)
                        </button>
                        <button
                            onClick={() =>
                                answerQuestion(game.currentPlayer.id, 2, false)
                            }
                            style={{ padding: "8px 12px" }}
                        >
                            Answer Wrong (lose 2)
                        </button>
                    </div>

                    <BoardView
                        board={game.board}
                        onTileClick={(tileId) =>
                            movePlayerToTile(game.currentPlayer.id, tileId)
                        }
                    />
                </>
            )}
        </div>
    );
}

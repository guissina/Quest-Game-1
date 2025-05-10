import { useState } from "react";
import { useBoard } from "../../hooks/data/useBoard";
import { Board } from "../../models/Board";

interface Props {
    onSelectBoard(board: Board): void;
}

export function BoardSelector({ onSelectBoard }: Props) {
    const { boards, loading, error } = useBoard();
    const [selectedId, setSelectedId] = useState("");

    const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const id = e.target.value;
        setSelectedId(id);
        if (!id) return;
        const board = boards.find((b) => b.id === id);
        if (board) onSelectBoard(board);
    };

    if (loading) return <p>Loading boards…</p>;
    if (error) return <p className='gp-error'>{error}</p>;

    return (
        <div className='gp-select-area'>
            <label htmlFor='board-select'>Selecione o tabuleiro:</label>
            <select
                id='board-select'
                value={selectedId}
                onChange={handleChange}
            >
                <option value=''>-- escolha um tabuleiro --</option>
                {boards.map((b) => (
                    <option key={b.id} value={b.id}>
                        {b.id}
                    </option>
                ))}
            </select>
        </div>
    );
}

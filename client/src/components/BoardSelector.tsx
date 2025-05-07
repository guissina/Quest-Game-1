import { Board } from "../models/Board";
import "./BoardSelector.scss";

interface Props {
    boards: Board[];
    selected: string;
    onSelect(id: string): void;
}

export function BoardSelector({ boards, selected, onSelect }: Props) {
    return (
        <div className='gp-select-area'>
            <label htmlFor='board-select' className='gp-label'>
                Select board:
            </label>
            <select
                id='board-select'
                value={selected}
                onChange={(e) => onSelect(e.target.value)}
                className='gp-select'
            >
                <option value=''>-- choose --</option>
                {boards.map((b) => (
                    <option key={b.id} value={b.id}>
                        {b.id}
                    </option>
                ))}
            </select>
        </div>
    );
}

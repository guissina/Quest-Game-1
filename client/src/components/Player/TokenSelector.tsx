import "./TokenSelector.scss";
import { useState } from "react";

interface Props {
    tokens: number[];
    onConfirm(steps: number): void;
    disabled?: boolean;
}

export function TokenSelector({ tokens, onConfirm, disabled }: Props) {
    const [selected, setSelected] = useState<number | null>(null);

    const handleClick = (value: number) => {
        if (!disabled) setSelected(value);
    };

    const handleConfirm = () => {
        if (selected !== null && !disabled) {
            onConfirm(selected);
            setSelected(null);
        }
    };

    return (
        <div className='gp-tokens'>
            <p>Select movement token:</p>
            <div className='gp-token-list'>
                {tokens.map((s) => (
                    <button
                        key={s}
                        className={`gp-token ${selected === s ? "selected" : ""}`}
                        onClick={() => handleClick(s)}
                        disabled={disabled}
                    >
                        {s}
                    </button>
                ))}
            </div>
            <button
                onClick={handleConfirm}
                disabled={selected === null || disabled}
                className='confirm-button'
            >
                Confirm Move
            </button>
        </div>
    );
}

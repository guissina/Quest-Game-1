import "./TokenSelector.scss";

interface Props {
    tokens: number[];
    onMove(steps: number): void;
}

export function TokenSelector({ tokens, onMove }: Props) {
    return (
        <div className='gp-tokens'>
            <p>Select movement token:</p>
            <div className='gp-token-list'>
                {tokens.map((s) => (
                    <button
                        key={s}
                        className='gp-token'
                        onClick={() => onMove(s)}
                    >
                        {s}
                    </button>
                ))}
            </div>
        </div>
    );
}

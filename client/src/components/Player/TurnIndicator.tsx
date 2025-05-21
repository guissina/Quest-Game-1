import "./TurnIndicator.scss";

interface TurnIndicatorProps {
    playerName: number;
}

export function TurnIndicator({ playerName }: TurnIndicatorProps) {
    return (
        <div className='gp-turn'>
            <strong>Turn:&nbsp;</strong>
            <span>{playerName}</span>
        </div>
    );
}

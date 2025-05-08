import "./TurnIndicator.scss";

interface Props {
    playerName: string;
}

export function TurnIndicator({ playerName }: Props) {
    return (
        <div className='gp-turn'>
            <strong>Turn:&nbsp;</strong>
            <span>{playerName}</span>
        </div>
    );
}

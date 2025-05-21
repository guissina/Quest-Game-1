export interface PlayerStateProps {
    playerId: number;
    tileId: number;
    tokens: number;
    mustAnswerBeforeMoving: boolean;
    isCurrentTurn: boolean;
}

export class PlayerState {
    public readonly playerId: number;
    public readonly tileId: number;
    public readonly tokens: number;
    public readonly mustAnswerBeforeMoving: boolean;
    public readonly isCurrentTurn: boolean;

    constructor(props: PlayerStateProps) {
        this.playerId = props.playerId;
        this.tileId = props.tileId;
        this.tokens = props.tokens;
        this.mustAnswerBeforeMoving = props.mustAnswerBeforeMoving;
        this.isCurrentTurn = props.isCurrentTurn;
    }
}

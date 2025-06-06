import { Board } from "./Board";
import { PlayerState, PlayerStateProps } from "./PlayerState";

export interface GameProps {
    sessionId: string
    board: Board;
    playerStates: PlayerStateProps[];
    finished: boolean;
    winnerId?: number;
}

export class Game {
    public readonly sessionId: string;
    public readonly board: Board;
    public readonly playerStates: PlayerState[];
    public readonly finished: boolean;
    public readonly winnerId: number | null;

    constructor(props: GameProps) {
        this.sessionId = props.sessionId;
        this.board = props.board;
        this.playerStates = props.playerStates.map((playerState) => new PlayerState(playerState));
        this.finished = props.finished;
        this.winnerId = props.winnerId ?? null;
    }
}

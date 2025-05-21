import { Board } from "./Board";
import { PlayerState } from "./PlayerState";

export interface GameProps {
    sessionId: string
    board: Board;
    playerStates: PlayerState[];
}

export class Game {
    public readonly sessionId: string;
    public readonly board: Board;
    public readonly playerStates: PlayerState[];

    constructor(props: GameProps) {
        this.sessionId = props.sessionId;
        this.board = props.board;
        this.playerStates = props.playerStates.map((playerState) => new PlayerState(playerState));
    }
}

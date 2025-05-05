import { Board } from "./Board";
import { Player } from "./Player";

export interface GameProps {
    id: string;
    board: Board;
    players: Player[];
}

export class Game {
    public readonly id: string;
    public readonly board: Board;
    public readonly players: Player[];
    public mustAnswerBeforeMoving: Record<string, boolean> = {};

    constructor(props: GameProps) {
        this.id = props.id;
        this.board = props.board;
        this.players = props.players;
        this.players.forEach(p => this.mustAnswerBeforeMoving[p.id] = false);
    }
}

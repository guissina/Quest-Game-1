import { Board } from "./Board";
import { Player } from "./Player";

export interface GameProps {
    id: string;
    board: Board;
    players: Player[];
    mustAnswerBeforeMoving?: Record<string, boolean>;
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
        this.mustAnswerBeforeMoving =
            props.mustAnswerBeforeMoving ||
            this.players.reduce((acc, p) => {
                acc[p.id] = false;
                return acc;
            }, {} as Record<string, boolean>);
    }

    public getPlayerById(playerId: string): Player {
        const player = this.players.find(p => p.id === playerId);
        if (!player) throw new Error(`Jogador ${playerId} não existe`);
        return player;
    }
}

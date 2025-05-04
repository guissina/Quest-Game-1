import { PlayerDeck } from "./PlayerDeck";

export interface PlayerProps {
    id: string;
    name: string;
}

export class Player {
    public readonly id: string;
    public readonly name: string;
    public movementTokens: number[];
    public specialDeck: PlayerDeck;

    constructor(props: PlayerProps) {
        this.id = props.id;
        this.name = props.name;
        this.movementTokens = [1, 2, 3, 4, 5];
        this.specialDeck = new PlayerDeck({ playerId: props.id });
    }

    public consumeToken(steps: number): void {
        const idx = this.movementTokens.indexOf(steps);
        if (idx < 0)
            throw new Error(`Token ${steps} not available for ${this.name}`);
        this.movementTokens.splice(idx, 1);
    }

    public addToken(steps: number): void {
        if (!this.movementTokens.includes(steps))
            this.movementTokens.push(steps);
    }
}

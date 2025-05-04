import { Card } from "./Card";

export interface PlayerDeckProps {
    playerId: string;
}

export class PlayerDeck {
    public readonly id: string;
    public readonly playerId: string;
    public Cards: Card[];

    constructor(props: PlayerDeckProps) {
        this.id = crypto.randomUUID();
        this.playerId = props.playerId;
        this.Cards = [];
    }
}

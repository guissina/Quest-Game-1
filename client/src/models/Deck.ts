import { Card } from "./Card";

export interface DeckProps {
    playerId: string;
}

export class Deck {
    public readonly id: string;
    public readonly playerId: string;
    public Cards: Card[];

    constructor(props: DeckProps) {
        this.id = crypto.randomUUID();
        this.playerId = props.playerId;
        this.Cards = [];
    }
}

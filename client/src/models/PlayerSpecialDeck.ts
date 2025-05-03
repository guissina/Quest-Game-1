import { SpecialCard } from "./SpecialCards";

export interface PlayerSpecialDeckProps {
    playerId: string;
}

export class PlayerSpecialDeck {
    id: string;
    playerId: string;
    specialCards: SpecialCard[];

    constructor({ playerId }: PlayerSpecialDeckProps) {
        this.id = '1';
        this.playerId = playerId;
        this.specialCards = [];
    }

    getCards(): SpecialCard[] {
        return this.specialCards;
    }

    addCard(card: SpecialCard): void {
        this.specialCards.push(card);
    }

    removeCard(cardId: string): void {
        this.specialCards = this.specialCards.filter(card => card.id !== cardId);
    }

    getCard(cardId: string): SpecialCard | undefined {
        return this.specialCards.find(card => card.id === cardId);
    }
}
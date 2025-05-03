import { SpecialCard } from "./SpecialCards";

export class PlayerSpecialDeck {
    readonly id: string;
    readonly playerId: string;
    private specialCards: SpecialCard[];

    constructor(id: string, playerId: string, specialCards: SpecialCard[]) {
        this.specialCards = specialCards.map(card => new SpecialCard(card.id, card.name, card.description, card.type, card.effect));
        this.id = id;
        this.playerId = playerId;
    }

    getCards(): SpecialCard[] {
        return [...this.specialCards];
    }

    addCard(card: SpecialCard): void {
        this.specialCards.push(new SpecialCard(card.id, card.name, card.description, card.type, card.effect));
    }

    removeCard(cardId: string): void {
        this.specialCards = this.specialCards.filter(card => card.id !== cardId);
    }

    getCard(cardId: string): SpecialCard | undefined {
        return this.specialCards.find(card => card.id === cardId);
    }
}
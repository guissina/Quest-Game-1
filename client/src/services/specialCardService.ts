import { SpecialCard } from "../models/Card";
import { PlayerSpecialDeck } from "../models/PlayerDeck";

export class SpecialCardService {
    addSpecialCardToDeck(deck: PlayerSpecialDeck, card: SpecialCard): void {
        deck.addCard(card);
    }

    removeSpecialCardFromDeck(deck: PlayerSpecialDeck, cardId: string): void {
        deck.removeCard(cardId);
    }

    getSpecialCardFromDeck(deck: PlayerSpecialDeck, cardId: string): SpecialCard | undefined {
        return deck.getCard(cardId);
    }
}
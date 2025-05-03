import { SpecialCard } from "../models/SpecialCards";
import { PlayerSpecialDeck } from "../models/PlayerSpecialDeck";

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
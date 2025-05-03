import { MovementToken } from "./MovementToken";
import { createTokensForPlayer } from "../services/gameServices";
import { PlayerSpecialDeck } from "./PlayerSpecialDeck";

export class Player {
    id: string;
    name: string;
    score: number;
    movementTokens: MovementToken[];
    specialDeck: PlayerSpecialDeck;

    constructor(id: string, name: string, score: number = 0, movementTokens: MovementToken[] = createTokensForPlayer(id), specialDeck: PlayerSpecialDeck = new PlayerSpecialDeck(id, id, [])) {
        this.specialDeck = specialDeck;
        this.id = id;
        this.name = name;
        this.score = score;
        this.movementTokens = movementTokens
    }
}
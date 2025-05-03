import { MovementToken } from "./MovementToken";
import { GameServices } from "../services/gameServices";
import { PlayerSpecialDeck } from "./PlayerSpecialDeck";

export interface PlayerProps {
    id: string;
    name: string;
}

export class Player {
    id: string;
    name: string;
    movementTokens: MovementToken[];
    specialDeck: PlayerSpecialDeck;

    constructor({ id, name }: PlayerProps) {
        this.id = id;
        this.name = name;
        this.movementTokens = GameServices.createTokensForPlayer(id);
        this.specialDeck = new PlayerSpecialDeck({ playerId: id });
    } 
}

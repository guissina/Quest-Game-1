import { createPlayer } from "../services/playerService";
import { MovementToken } from "./MovementToken";
import { createTokensForPlayer } from "../services/gameServices";

export class Player {
    id: string;
    name: string;
    score: number;
    movementTokens: MovementToken[];

    constructor(id: string, name: string, score: number = 0, movementTokens: MovementToken[] = createTokensForPlayer(id)) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.movementTokens = movementTokens
    }
}
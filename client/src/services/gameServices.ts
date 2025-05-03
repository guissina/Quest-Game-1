import { MovementToken } from "../models/MovementToken";

export class GameServices {
    static createTokensForPlayer(playerId: string): MovementToken[] {
        return [1, 2, 3, 4, 5].map(value => 
            new MovementToken({
                id: `${playerId}-${value}`,
                value,
                isLost: false
            })
        );
    }
}

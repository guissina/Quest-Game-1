import { MovementToken } from "../models/MovementToken";

export function createTokensForPlayer(id: string): MovementToken[] {
    return [1, 2, 3, 4, 5].map(v => new MovementToken(`${id}-${v}`, v, false));
}
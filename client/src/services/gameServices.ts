import { Player } from "../models/Player";
import { MovementToken } from "../models/MovementToken";
import { Board } from "../models/Board";
import { api } from "./api";

export function createTokensForPlayer(id: string): MovementToken[] {
    return [1, 2, 3, 4, 5].map(v => new MovementToken(`${id}-${v}`, v, false));
}
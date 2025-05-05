import { Player } from "../../models/Player";

export class TurnManager {
    private currentTurn = 0;

    public getCurrentPlayer(players: Player[]): Player {
        return players[this.currentTurn];
    }

    public verifyTurn(playerId: string, players: Player[]) {
        const current = this.getCurrentPlayer(players);
        if (current.id !== playerId)
            throw new Error(`Não é a vez de ${playerId}`);
    }

    public next(players: Player[]) {
        this.currentTurn = (this.currentTurn + 1) % players.length;
    }
}

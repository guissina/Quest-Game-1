import { Game } from "../../models/Game";
import { Tile } from "../../models/Tile";

export class BoardManager {
    public seed(aggregate: Game) {
        const start = aggregate.board.tiles[0];
        this.clearAll(aggregate);
        start.players.push(...aggregate.players);
    }

    public moveTo(aggregate: Game, playerId: string, tileId: string) {
        this.clearFromAll(aggregate, playerId);
        const dest = this.findTile(aggregate, tileId);
        dest.players.push(aggregate.players.find((p) => p.id === playerId)!);
    }

    private clearAll(aggregate: Game) {
        aggregate.board.tiles.forEach((t) => (t.players = []));
    }

    private clearFromAll(aggregate: Game, playerId: string) {
        aggregate.board.tiles.forEach(
            (t) => (t.players = t.players.filter((p) => p.id !== playerId))
        );
    }

    private findTile(aggregate: Game, tileId: string): Tile {
        const tile = aggregate.board.tiles.find((t) => t.id === tileId);
        if (!tile) 
            throw new Error(`Tile ${tileId} não existe`);
        return tile;
    }
}

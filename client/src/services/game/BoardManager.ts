import { Game } from "../../models/Game";

export class BoardManager {
    
    seed(aggregate: Game) {
        const start = aggregate.board.tiles[0];
        this.clearAll(aggregate);
        start.players.push(...aggregate.players);
    }

    moveTo(aggregate: Game, playerId: string, tileId: string) {
        this.clearFromAll(aggregate, playerId);
        const dest = this.findTile(aggregate, tileId);
        dest.players.push(aggregate.players.find((p) => p.id === playerId)!);
    }

    moveBySteps(aggregate: Game, playerId: string, steps: number) {
        const origin = this.findPlayerTile(aggregate, playerId);
        const idx = aggregate.board.tiles.indexOf(origin);
        const dest =
            aggregate.board.tiles[(idx + steps) % aggregate.board.tiles.length];
        origin.players = origin.players.filter((p) => p.id !== playerId);
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

    private findTile(aggregate: Game, tileId: string) {
        const tile = aggregate.board.tiles.find((t) => t.id === tileId);
        if (!tile) throw new Error(`Tile ${tileId} não existe`);
        return tile;
    }

    private findPlayerTile(aggregate: Game, playerId: string) {
        const tile = aggregate.board.tiles.find((t) =>
            t.players.some((p) => p.id === playerId)
        );
        if (!tile)
            throw new Error(`Jogador ${playerId} não está em nenhuma tile`);
        return tile;
    }
}

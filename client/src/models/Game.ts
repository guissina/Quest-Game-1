import { Board } from "./Board";
import { Player } from "./Player";

export interface GameProps {
    id: string;
    board: Board;
    players: Player[];
}

export class Game {
    public readonly id: string;
    public readonly board: Board;
    public readonly players: Player[];
    
    protected currentTurn: number;

    constructor({ id, board, players }: GameProps) {
        this.id = id;
        this.board = board;
        this.players = players;
        this.currentTurn = 0;
    }

    public clone(): Game {
        const clone = new Game({
            id: this.id,
            board: this.board,
            players: this.players,
        });
        clone.currentTurn = this.currentTurn;
        return clone;
    }
    
    public seedPlayers(): void {
        const start = this.board.tiles[0];
        this.clearAllTiles();
        this.players.forEach((p) => start.players.push(p));
    }

    public get currentPlayer(): Player {
        return this.players[this.currentTurn];
    }

    public advanceTurn(): void {
        this.currentTurn = (this.currentTurn + 1) % this.players.length;
    }

    public moveToTile(playerId: string, tileId: string): void {
        this.assertIsCurrentPlayer(playerId);

        this.clearPlayerFromTiles(playerId);
        const destination = this.findTileOrThrow(tileId);
        destination.players.push(this.currentPlayer);

        this.advanceTurn();
    }

    public moveBySteps(playerId: string, steps: number): void {
        const origin = this.findPlayerTileOrThrow(playerId);
        const originIndex = this.board.tiles.indexOf(origin);

        const destIndex = (originIndex + steps) % this.board.tiles.length;
        const destination = this.board.tiles[destIndex];

        origin.players = origin.players.filter((p) => p.id !== playerId);
        destination.players.push(this.players.find((p) => p.id === playerId)!);
        this.advanceTurn();
    }

    private clearAllTiles(): void {
        this.board.tiles.forEach((t) => (t.players = []));
    }

    private clearPlayerFromTiles(playerId: string): void {
        this.board.tiles.forEach((t) => {
            t.players = t.players.filter((p) => p.id !== playerId);
        });
    }

    private assertIsCurrentPlayer(playerId: string): void {
        if (this.currentPlayer.id !== playerId)
            throw new Error(`Não é a vez de ${playerId}`);
    }

    private findTileOrThrow(tileId: string) {
        const tile = this.board.tiles.find((t) => t.id === tileId);
        if (!tile) throw new Error(`Tile "${tileId}" não existe`);
        return tile;
    }

    private findPlayerTileOrThrow(playerId: string) {
        const tile = this.board.tiles.find((t) =>
            t.players.some((p) => p.id === playerId)
        );
        if (!tile)
            throw new Error(`Player "${playerId}" não está em nenhuma tile`);
        return tile;
    }
}

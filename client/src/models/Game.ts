import { Board } from "./Board";
import { Player } from "./Player";

export interface GameProps {
    id: string;
    board: Board;
    players: Player[];
}

export class Game {
    id: string;
    board: Board;
    players: Player[];

    constructor({ id, board, players }: GameProps) {
        this.id = id;
        this.board = board;
        this.players = players;
    }

    initializePlayers() {
        if (this.board.tiles.length === 0) return;
        
        const firstTile = this.board.tiles[0];
        this.players.forEach((player) => {
            firstTile.players.push(player);
        });
    }

    movePlayerTo(playerId: string, targetTileId: string) {
        const player = this.players.find((p) => p.id === playerId);
        if (!player) throw new Error(`Player ${playerId} não existe no jogo`);
    
        this.board.tiles.forEach((tile) => {
            tile.players = tile.players.filter((p) => p.id !== playerId);
        });
    
        const target = this.board.tiles.find((t) => t.id === targetTileId);
        if (!target) throw new Error(`Tile ${targetTileId} não existe no board`);
        target.players.push(player);
    }

    movePlayerBy(playerId: string, steps: number) {
        const player = this.players.find((p) => p.id === playerId);
        if (!player) throw new Error(`Player ${playerId} não existe no jogo`);

        const currentTile = this.board.tiles.find((tile) =>
            tile.players.some((p) => p.id === playerId)
        );
        if (!currentTile)
            throw new Error(`Player ${playerId} não está em nenhuma tile`);

        const currentIndex = this.board.tiles.indexOf(currentTile);
        const targetIndex = (currentIndex + steps) % this.board.tiles.length;
        const targetTile = this.board.tiles[targetIndex];

        currentTile.players = currentTile.players.filter(
            (p) => p.id !== playerId
        );
        targetTile.players.push(player);
    }
}
import { Tile, TileProps } from "./Tile";

export interface BoardProps {
    id: string;
    layoutId: string;
    tiles: TileProps[];
}

export class Board {
    id: string;
    layoutId: string;
    tiles: Tile[];

    constructor({ id, layoutId, tiles }: BoardProps) {
        this.id = id;
        this.layoutId = layoutId;
        this.tiles = tiles.map((props) => new Tile(props));
    }
}

import { Tile, TileProps } from "./Tile";

export interface BoardProps {
    id: number;
    name: string;
    rows: number;
    cols: number;
    tiles?: TileProps[];
}

export class Board {
    public readonly id: number;
    public readonly name: string;
    public readonly rows: number;
    public readonly cols: number;
    public readonly tiles: Tile[];

    constructor(props: BoardProps) {
        this.id = props.id;
        this.name = props.name;
        this.rows = props.rows;
        this.cols = props.cols;
        this.tiles = props.tiles ? props.tiles.map((tile) => new Tile(tile)) : [];
    }
}

import { Theme, ThemeProps } from "./Theme";

export interface TileProps {
    id: number;
    row: number;
    col: number;
    theme?: ThemeProps;
}

export class Tile {
    public readonly id: number;
    public readonly row: number;
    public readonly col: number;
    public readonly theme: Theme | null;

    constructor(props: TileProps) {
        this.id = props.id;
        this.row = props.row;
        this.col = props.col;
        this.theme = props.theme ? new Theme(props.theme) : null;
    }
}

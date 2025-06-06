import { Theme, ThemeProps } from "./Theme";

export interface TileProps {
    id: number;
    row: number;
    col: number;
    sequence: number;
    themes?: ThemeProps[];
}

export class Tile {
    public readonly id: number;
    public readonly row: number;
    public readonly col: number;
    public readonly sequence: number;
    public readonly themes: Theme[];

    constructor(props: TileProps) {
        this.id = props.id;
        this.row = props.row;
        this.col = props.col;
        this.sequence = props.sequence;
        this.themes = props.themes?.map(theme => new Theme(theme)) || [];
    }
}

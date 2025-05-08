import { Card, CardProps } from "./Card";
import { Player } from "./Player";
import { Theme, ThemeProps } from "./Theme";

export interface TileProps {
    id: string;
    row: number;
    col: number;
    specialCard?: CardProps;
    questionTheme?: ThemeProps;
}

export class Tile {
    public readonly id: string;
    public readonly row: number;
    public readonly col: number;
    public questionTheme: Theme | null;
    public specialCard: Card | null;
    public players: Player[];

    constructor(props: TileProps) {
        this.id = props.id;
        this.row = props.row;
        this.col = props.col;
        this.questionTheme = props.questionTheme ? new Theme(props.questionTheme) : null;
        this.specialCard = props.specialCard ? new Card(props.specialCard) : null;
        this.players = [];
    }
}

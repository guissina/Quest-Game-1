import { Player } from "./Player";
import { Theme } from "../enums/Theme";

export interface TileProps {
    id: string;
    questionTheme: Theme;
    players: Player[];
}

export class Tile {
    id: string;
    questionTheme: Theme;
    players: Player[];

    constructor(props: TileProps) {
        this.id = props.id;
        this.questionTheme = props.questionTheme;
        this.players = props.players;
    }
}

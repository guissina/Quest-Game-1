import { Player } from "./Player";
import { Theme } from "../enums/Theme";
import { SpecialCard } from "./SpecialCards";

export interface TileProps {
    id: string;
    questionTheme: Theme;
    players: Player[];
    specialCard?: SpecialCard;
}

export class Tile {
    id: string;
    questionTheme: Theme;
    players: Player[];
    specialCard?: SpecialCard;

    constructor(props: TileProps) {
        this.id = props.id;
        this.questionTheme = props.questionTheme;
        this.players = props.players;
        this.specialCard = props.specialCard;
    }
}

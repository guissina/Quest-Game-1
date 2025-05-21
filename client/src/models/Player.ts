export interface PlayerProps {
    id: string;
    name: string;
    tokens: number[];
}

export class Player {
    public readonly id: string;
    public readonly name: string;
    public tokens: number[];

    constructor(props: PlayerProps) {
        this.id = props.id;
        this.name = props.name;
        this.tokens = props.tokens;
    }
}

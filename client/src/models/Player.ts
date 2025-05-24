export interface PlayerProps {
    id: number;
    name: string;
}

export class Player {
    public readonly id: number;
    public readonly name: string;

    constructor(props: PlayerProps) {
        this.id = props.id;
        this.name = props.name;
    }
}

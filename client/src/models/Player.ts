export interface PlayerProps {
    id: string;
    name: string;
}

export class Player {
    public readonly id: string;
    public readonly name: string;

    constructor(props: PlayerProps) {
        this.id = props.id;
        this.name = props.name;
    }
}

export interface CardProps {
    id: string;
    name: string;
    description: string;
    type: string;
    effect: string;
}

export class Card {
    public readonly id: string;
    public readonly name: string;
    public readonly description: string;
    public readonly type: string;
    public readonly effect: string;

    constructor(props: CardProps) {
        this.id = props.id;
        this.name = props.name;
        this.description = props.description;
        this.type = props.type;
        this.effect = props.effect;
    }
}
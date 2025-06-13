export interface PlayerProps {
    id: number;
    name: string;
    email: string;
    balance: number;
    avatarIndex?: number;
}

export class Player {
    public readonly id: number;
    public readonly name: string;
    public readonly email: string;
    public readonly balance: number = 0;
    public readonly avatarIndex: number;

    constructor(props: PlayerProps) {
        this.id = props.id;
        this.name = props.name;
        this.email = props.email;
        this.balance = props.balance;
        this.avatarIndex = props.avatarIndex ?? 0;
    }
}

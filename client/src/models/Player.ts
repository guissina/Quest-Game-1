export interface PlayerProps {
    id: number;
    name: string;
    email: string;
    balance: number;
    avatarIndex?: number;
    themeIds?: number[];
    password?: string;
}

export class Player {
    public readonly id: number;
    public readonly name: string;
    public readonly email: string;
    public readonly balance: number = 0;
    public readonly themeIds: number[];
    public readonly password?: string;
    public readonly avatarIndex: number;

    constructor(props: PlayerProps) {
        this.id = props.id;
        this.name = props.name;
        this.email = props.email;
        this.balance = props.balance;
        this.avatarIndex = props.avatarIndex ?? 0;
        this.password = props.password;
        this.themeIds = props.themeIds ?? [];
    }
}

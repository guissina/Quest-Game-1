export interface ThemeProps {
    id: number;
    code: string;
    name: string;
    free: boolean;
    cost: number;
}

export class Theme {
    public readonly id: number;
    public readonly code: string;
    public readonly name: string;
    public readonly free: boolean;
    public readonly cost: number;

    constructor(props: ThemeProps) {
        this.id = props.id;
        this.code = props.code;
        this.name = props.name;
        this.free = props.free;
        this.cost = props.cost;
    }
}

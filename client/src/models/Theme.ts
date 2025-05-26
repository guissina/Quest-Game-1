export interface ThemeProps {
    id: string;
    code: string;
    name: string;
    free: boolean;
}

export class Theme {
    public readonly id: string;
    public readonly code: string;
    public readonly name: string;
    public readonly free: boolean;

    constructor(props: ThemeProps) {
        this.id = props.id;
        this.code = props.code;
        this.name = props.name;
        this.free = props.free;
    }
}

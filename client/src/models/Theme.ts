export interface ThemeProps {
    id: string;
    code: string;
    name: string;
}

export class Theme {
    public readonly id: string;
    public readonly code: string;
    public readonly name: string;

    constructor(props: ThemeProps) {
        this.id = props.id;
        this.code = props.code;
        this.name = props.name;
    }
}

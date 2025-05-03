export interface MovementTokenProps {
    id: string;
    value: number;
    isLost: boolean;
}

export class MovementToken {
    id: string;
    value: number;
    isLost: boolean;

    constructor({ id, value, isLost }: MovementTokenProps) {
        this.id = id;
        this.value = value;
        this.isLost = isLost;
    }
}
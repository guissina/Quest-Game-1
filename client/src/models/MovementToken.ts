export class MovementToken {
    id: string;
    value: number;
    isLost: boolean;

    constructor(id: string, value: number, isLost: boolean) {
        this.id = id;
        this.value = value;
        this.isLost = isLost;
    }
}
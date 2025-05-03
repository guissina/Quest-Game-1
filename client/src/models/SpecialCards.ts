export interface SpecialCardProps {
    id: string;
    name: string;
    description: string;
    type: string;
    effect: string;
}

export class SpecialCard {
    id: string;
    name: string;
    description: string;
    type: string;
    effect: string;

    constructor({ id, name, description, type, effect }: SpecialCardProps) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.effect = effect;
    }
}
export class SpecialCard {
    id: string;
    name: string;
    description: string;
    type: string;
    effect: string;

    constructor(id: string, name: string, description: string, type: string, effect: string) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.effect = effect;
    }

}
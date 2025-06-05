import { Question, QuestionProps } from "./Question";

export interface AbilityStateProps {
    active: boolean;
}

export enum AbilityType {
    FREE_MOVEMENT = "FREE_MOVEMENT",
    ROLL_DICE = "ROLL_DICE",
    SKIP_OPPONENT_TURN = "SKIP_OPPONENT_TURN",
    TOKEN_THEFT = "TOKEN_THEFT",
    RETURN_TILE = "RETURN_TILE",
    BLOCK_TILE = "BLOCK_TILE",
    REVERSE_MOVEMENT = "REVERSE_MOVEMENT",
    SHUFFLE_CARDS = "SHUFFLE_CARDS"
}


export interface PlayerStateProps {
    playerId: number;
    tileId: number;
    tokens: number[];
    isCurrentTurn: boolean;
    pendingQuestion?: QuestionProps;
    pendingSteps?: number;
    abilities: Record<string, boolean>;
}

export class PlayerState {
    public readonly playerId: number;
    public readonly tileId: number;
    public readonly tokens: number[];
    public readonly isCurrentTurn: boolean;
    public readonly pendingQuestion: Question | null;
    public readonly pendingSteps: number | null;
    public readonly correctCount: number;
    public readonly abilities: Map<AbilityType, boolean>;

    constructor(props: PlayerStateProps) {
        this.playerId = props.playerId;
        this.tileId = props.tileId;
        this.tokens = props.tokens;
        this.isCurrentTurn = props.isCurrentTurn;
        this.pendingQuestion = props.pendingQuestion ? new Question(props.pendingQuestion) : null;
        this.pendingSteps = props.pendingSteps ?? null;
        this.correctCount = 0;

        this.abilities = new Map();
        if (props.abilities) {
            Object.entries(props.abilities).forEach(([key, value]) => {
                this.abilities.set(key as AbilityType, value);
            });
        }


    }

    public hasAbility(abilityType: AbilityType): boolean {
        return this.abilities.has(abilityType);
    }

    public isAbilityActive(abilityType: AbilityType): boolean {
        return this.abilities.get(abilityType) ?? false; // Mudança aqui
    }

    public getAvailableAbilities(): AbilityType[] {
        return Array.from(this.abilities.keys()).filter(type =>
            this.isAbilityActive(type)
        );
    }
}

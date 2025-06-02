import { Question, QuestionProps } from "./Question";

export interface PlayerStateProps {
    playerId: number;
    tileId: number;
    tokens: number[];
    isCurrentTurn: boolean;
    pendingQuestion?: QuestionProps;
    pendingSteps?: number;
}

export class PlayerState {
    public readonly playerId: number;
    public readonly tileId: number;
    public readonly tokens: number[];
    public readonly isCurrentTurn: boolean;
    public readonly pendingQuestion: Question | null;
    public readonly pendingSteps: number | null;

    constructor(props: PlayerStateProps) {
        this.playerId = props.playerId;
        this.tileId = props.tileId;
        this.tokens = props.tokens;
        this.isCurrentTurn = props.isCurrentTurn;
        this.pendingQuestion = props.pendingQuestion ? new Question(props.pendingQuestion) : null;
        this.pendingSteps = props.pendingSteps ?? null;
    }
}

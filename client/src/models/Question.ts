export interface QuestionOptionProps {
    id: number;
    text: string;
    correct?: boolean;
}

export class QuestionOption {
    public readonly id: number;
    public readonly text: string;
    public readonly correct: boolean;

    constructor(props: QuestionOptionProps) {
        this.id = props.id;
        this.text = props.text;
        this.correct = props.correct ?? false;
    }
}

export interface QuestionProps {
    id: number;
    text: string;
    answer: string;
    difficulty: string;
    options: QuestionOptionProps[];
    themeId?: string;
}

export class Question {
    public readonly id: number;
    public readonly text: string;
    public readonly answer: string;
    public readonly difficulty: string;
    public readonly options: QuestionOption[];
    public readonly themeId?: string;

    constructor(props: QuestionProps) {
        this.id = props.id;
        this.text = props.text;
        this.answer = props.answer;
        this.difficulty = props.difficulty;
        this.options = props.options.map((o) => new QuestionOption(o));
        this.themeId = props.themeId;
    }

    public getOptionById(optionId: number) {
        return this.options.find((o) => o.id === optionId);
    }
}

export interface QuestionProps {
    id: string;
    text: string;
    answer: string;
    difficulty: string;
}

export class Question {
    public readonly id: string;
    public readonly text: string;
    public readonly answer: string;
    public readonly difficulty: string;

    constructor(props: QuestionProps) {
        this.id = props.id;
        this.text = props.text;
        this.answer = props.answer;
        this.difficulty = props.difficulty;
    }
}

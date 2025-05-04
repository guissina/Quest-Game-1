import { Question, QuestionProps } from "./Question";

export interface ThemeProps {
    id: string;
    code: string;
    name: string;
    questions: QuestionProps[];
}

export class Theme {
    public readonly id: string;
    public readonly code: string;
    public readonly name: string;
    public readonly questions: Question[];

    constructor(props: ThemeProps) {
        this.id = props.id;
        this.code = props.code;
        this.name = props.name;
        this.questions = props.questions.map(question => new Question(question));
    }
}

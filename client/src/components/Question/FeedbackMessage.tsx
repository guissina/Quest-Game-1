import "./FeedbackMessage.scss";

interface Props {
    correct: boolean;
    correctAnswer: string;
}

export function FeedbackMessage({ correct, correctAnswer }: Props) {
    return (
        <div
            className={
                correct
                    ? "gp-feedback gp-feedback-correct"
                    : "gp-feedback gp-feedback-wrong"
            }
        >
            {correct
                ? "🎉 Você acertou!"
                : `❌ Errou! A resposta certa era "${correctAnswer}".`}
        </div>
    );
}

import { useState } from "react";
import { Question } from "../../models/Question";
import "./QuestionModal.scss";

interface QuestionModalProps {
    question: Question;
    onSubmit: (answer: string) => void;
    onCancel: () => void;
}

export function QuestionModal({
    question,
    onSubmit,
    onCancel,
}: QuestionModalProps) {
    const [answer, setAnswer] = useState("");

    return (
        <div className='qm-overlay'>
            <div className='qm-modal'>
                <h3 className='qm-title'>Pergunta</h3>
                <p className='qm-text'>{question.text}</p>
                <input
                    value={answer}
                    onChange={(e) => setAnswer(e.target.value)}
                    className='qm-input'
                />
                <div className='qm-actions'>
                    <button onClick={onCancel} className='qm-btn qm-cancel'>
                        Cancelar
                    </button>
                    <button
                        onClick={() => onSubmit(answer)}
                        className='qm-btn qm-submit'
                    >
                        Responder
                    </button>
                </div>
            </div>
        </div>
    );
}

import Modal from "react-modal";
import { Question } from "../../models/Question";
import "./QuestionModal.scss";

Modal.setAppElement("#root"); 

interface QuestionModalProps {
    isOpen: boolean;
    question: Question;
    canAnswer: boolean;
    onRequestClose: () => void;
    onAnswer: (optId: number) => void;
}

export function QuestionModal({ isOpen, question, canAnswer, onRequestClose, onAnswer }: QuestionModalProps) {
    return (
        <Modal
            isOpen={isOpen}
            onRequestClose={onRequestClose}
            contentLabel='Question Modal'
        >
            <h2>Question</h2>
            <p>{question.text}</p>
            <ul>
                {question.options.map((opt) => (
                    <li key={opt.id}>
                        <button 
                            onClick={() => onAnswer(opt.id)}
                            disabled={!canAnswer}
                            className={!canAnswer ? "disabled" : ""}
                        >
                            {opt.text}
                        </button>
                    </li>
                ))}
            </ul>
            <button onClick={onRequestClose}>Cancel</button>
        </Modal>
    );
}

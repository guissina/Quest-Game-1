import { useEffect } from 'react';
import Modal from 'react-modal';
import { Question } from '../../models/Question';
import styles from './QuestionModal.module.scss';

Modal.setAppElement('#root');

interface QuestionModalProps {
    isOpen: boolean;
    question: Question;
    canAnswer: boolean;
    onRequestClose: () => void;
    onAnswer: (optId: number) => void;
}

export default function QuestionModal({ isOpen, question, canAnswer, onRequestClose, onAnswer }: QuestionModalProps) {

    useEffect(() => {
        if (isOpen) {
            const btn = document.querySelector(`.${styles.optionBtn}`) as HTMLButtonElement;
            btn?.focus();
        }
    }, [isOpen]);

    return (
        <Modal
            isOpen={isOpen}
            onRequestClose={onRequestClose}
            overlayClassName={{
                base: styles.overlay,
                afterOpen: styles.overlay + '--after-open',
                beforeClose: styles.overlay + '--before-close'
            }}
            className={{
                base: styles.content,
                afterOpen: styles['content--after-open'],
                beforeClose: styles['content--before-close']
            }}
            closeTimeoutMS={200}
        >
            <div className={styles.header}>
                <h2 className={styles.title} data-text="Question">Question</h2>
                <button
                    onClick={onRequestClose}
                    className={styles.closeBtn}
                    aria-label="Close"
                >
                    ✕
                </button>
            </div>

            <p className={styles.prompt}>{question.text}</p>

            <ul className={styles.options}>
                {question.options.map(opt => (
                    <li key={opt.id}>
                        <button
                            className={`${styles.optionBtn} secondary-btn`}
                            onClick={() => onAnswer(opt.id)}
                            disabled={!canAnswer}
                        >
                            {opt.text}
                        </button>
                    </li>
                ))}
            </ul>
        </Modal>
    );
}

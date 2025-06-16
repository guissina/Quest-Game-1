import { useEffect, useState } from 'react';
import Modal from 'react-modal';
import { Question, QuestionOption } from '../../models/Question';
import styles from './QuestionModal.module.scss';

Modal.setAppElement('#root');

interface QuestionModalProps {
  isOpen: boolean;
  question: Question;
  canAnswer: boolean;
  onRequestClose: () => void;
  onAnswer: (optId: number) => void;
}

function shuffle<T>(arr: T[]): T[] {
  const a = [...arr];
  for (let i = a.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [a[i], a[j]] = [a[j], a[i]];
  }
  return a;
}

export default function QuestionModal({
  isOpen,
  question,
  canAnswer,
  onRequestClose,
  onAnswer,
}: QuestionModalProps) {
  const [shuffledOptions, setShuffledOptions] = useState<QuestionOption[]>([]);

  useEffect(() => {
    if (isOpen) {
      const btn = document.querySelector(
        `.${styles.optionBtn}`,
      ) as HTMLButtonElement;
      btn?.focus();
    }
  }, [isOpen]);

  useEffect(() => {
    setShuffledOptions(shuffle(question.options));
  }, [question]);

  return (
    <Modal
      isOpen={isOpen}
      // Bloqueia fechar clicando no overlay se canAnswer for true
      shouldCloseOnOverlayClick={!canAnswer}
      // Bloqueia fechar com Esc se canAnswer for true
      shouldCloseOnEsc={!canAnswer}
      // Só permite onRequestClose se canAnswer for false
      onRequestClose={() => {
        if (!canAnswer) onRequestClose();
      }}
      overlayClassName={{
        base: styles.overlay,
        afterOpen: styles.overlay + '--after-open',
        beforeClose: styles.overlay + '--before-close',
      }}
      className={{
        base: styles.content,
        afterOpen: styles['content--after-open'],
        beforeClose: styles['content--before-close'],
      }}
      closeTimeoutMS={200}
    >
      <div className={styles.header}>
        <h2 className={styles.title} data-text="Question">
          Pergunta
        </h2>
        {!canAnswer && (
          <button
            onClick={onRequestClose}
            className={styles.closeBtn}
            aria-label="Close"
          >
            ✕
          </button>
        )}
      </div>

      <p className={styles.prompt}>{question.text}</p>

      <ul className={styles.options}>
        {shuffledOptions.map((opt: QuestionOption) => (
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

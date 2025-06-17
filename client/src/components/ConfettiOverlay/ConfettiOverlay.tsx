import { useWindowSize } from 'react-use';
import Confetti from 'react-confetti';
import styles from './ConfettiOverlay.module.scss';

interface ConfettiOverlayProps {
    winnerName: string;
    running: boolean;
    isHost: boolean;
    onStop: () => void;
}

export default function ConfettiOverlay({ winnerName, running, isHost, onStop }: ConfettiOverlayProps) {
    const { width, height } = useWindowSize();
    if (!running) return null;

    return (
        <div className={styles.overlay}>
            <Confetti width={width} height={height} numberOfPieces={500} recycle />
            <div className={styles.content}>
                <h1>🎉 Vitória de {winnerName}! 🏆</h1>
                <p>Obrigado por jogar.</p>

                {isHost ? (
                    <button onClick={onStop} className="btn">
                        Encerrar celebração
                    </button>
                ) : (
                    <p className={styles.notice}>
                        Aguardando o host encerrar a celebração...
                    </p>
                )}
            </div>
        </div>
    );
}

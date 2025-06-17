import styles from './TimerCountdown.module.scss';

interface TimerCountdownProps {
    initialTime: number;
}

export default function TimerCountdown({ initialTime }: TimerCountdownProps) {
    const formatTime = (seconds: number) => {
        const min = Math.floor(seconds / 60);
        const sec = seconds % 60;
        return `${min}:${sec < 10 ? '0' : ''}${sec}`;
    };

    const isWarning = initialTime <= 10;

    return (
        <div className={styles.timerContainer}>
            <span
                className={`${styles.timerText} ${isWarning ? styles.warning : ''
                    }`}
            >
                {formatTime(initialTime)}
            </span>
        </div>
    );
}

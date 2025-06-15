import styles from './GameHeader.module.scss';

interface GameHeaderProps {
    currentPlayerName?: string;
}

export default function GameHeader({ currentPlayerName }: GameHeaderProps) {
    return (
        <header className={styles.gameHeader}>
            <div className={styles.turnIndicator}>
                <strong>Turn:</strong> <span>{currentPlayerName}</span>
            </div>
        </header>
    );
}

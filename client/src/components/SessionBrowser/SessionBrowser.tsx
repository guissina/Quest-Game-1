import styles from './SessionBrowser.module.scss';
import { UsersRound } from 'lucide-react';
import avatar1 from '../../assets/avatar/avatar1.png';
import avatar2 from '../../assets/avatar/avatar2.png';
// import avatar3 from '../../assets/avatar/avatar3.png';
import avatar4 from '../../assets/avatar/avatar4.png';
import avatar5 from '../../assets/avatar/avatar5.png';

interface SessionBrowserProps {
    onJoinRoom: (sessionId: string) => void;
    onCreateRoom: () => void;
}

const players = [
    { id: 'sess-1', name: 'Niwan', avatar: avatar2, disabled: true },
    { id: 'sess-2', name: 'Joãozinho da 12', avatar: avatar5 },
    { id: 'sess-3', name: 'Pedrinho Matador', avatar: avatar1 },
    { id: 'sess-4', name: 'meMataDeUmaVez',  avatar: avatar4 }
];

export default function SessionBrowser({ onJoinRoom, onCreateRoom }: SessionBrowserProps) {
    return (
        <div className={styles.sessionBrowser}>
            <header className={styles.header}>
                <UsersRound size={48} />
                <h2>Salas Disponíveis</h2>
            </header>

            <ul className={styles.sessionList}>
                {players.map((player) => (
                    <li key={player.id} className={styles.sessionItem}>
                        <img
                            src={player.avatar}
                            alt={`Avatar de ${player.name}`}
                            width={80}
                            height={80}
                        />
                        <p className={styles.playerName}>{player.name}</p>
                        <button
                            disabled={player.disabled}
                            className={`secondary-btn ${player.disabled ? styles.disabled : ''}`}
                            onClick={() => onJoinRoom(player.id)}
                        >
                            {player.disabled ? 'Em Jogo' : 'Jogar junto'}
                        </button>
                    </li>
                ))}
            </ul>

            <footer className={styles.footer}>
                <button
                    className="btn"
                    type="button"
                    onClick={onCreateRoom}
                >
                    Criar Sala
                </button>
            </footer>
        </div>
    );
}

import styles from './SessionBrowser.module.scss';
import { Gamepad2 } from 'lucide-react';
import { Room } from '../../models/Room';
import avatar1 from '../../assets/avatar/avatar1.png';

interface SessionBrowserProps {
    publicRooms: Room[];
    onJoinRoom: (sessionId: string) => void;
    onCreateRoom: () => void;
}

export default function SessionBrowser({ publicRooms, onJoinRoom, onCreateRoom }: SessionBrowserProps) {
    return (
        <div className={styles.sessionBrowser}>
            <header className={styles.header}>
                <Gamepad2 size={48} />
                <h2>Sessões Ativas</h2>
            </header>

            <ul className={styles.sessionList}>
                {publicRooms.length === 0 && (
                    <li className={styles.empty}>Nenhuma sala pública encontrada.</li>
                )}

                {publicRooms.map(room => (
                    <li key={room.sessionId} className={styles.sessionItem}>
                        <img
                            src={avatar1}
                            alt={`Avatar de ....`}
                            width={80}
                            height={80}
                        />
                        <p><strong>HOST:</strong> {room.hostId ?? '—'}</p>
                        <p><strong>JOGADORES:</strong> {room.playerCount}</p>
                        <button
                            className="secondary-btn"
                            onClick={() => onJoinRoom(room.sessionId)}
                        >
                        Entrar
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

import styles from './SessionForm.module.scss';
import { FormEvent, useState } from 'react';

interface SessionFormProps {
    onJoin: (id: string) => void;
}

export default function SessionForm({ onJoin }: SessionFormProps) {
    const [sessionId, setSessionId] = useState('');
    const handleSubmit = (e: FormEvent) => {
        e.preventDefault();
        if (sessionId.trim()) onJoin(sessionId.trim());
    };

    return (
        <form className={styles.sessionForm} onSubmit={handleSubmit}>
            <label htmlFor="session">
                <input
                    id="session"
                    name="session"
                    type="text"
                    placeholder="ID da sessão"
                    value={sessionId}
                    onChange={e => setSessionId(e.currentTarget.value)}
                />
            </label>
            <button className="secondary-btn" type="submit">
                Entrar na partida
            </button>
        </form>
    );
}

import { useEffect, useState } from 'react';
import { Player } from '../../models/Player';
import { Theme, ThemeProps } from '../../models/Theme';
import styles from './Lobby.module.scss';

import image1 from '../../assets/avatar/avatar1.png';
import image2 from '../../assets/avatar/avatar2.png';
import image3 from '../../assets/avatar/avatar3.png';
import image4 from '../../assets/avatar/avatar4.png';
import image5 from '../../assets/avatar/avatar5.png';
import image6 from '../../assets/avatar/avatar6.png';
const images = [image1, image2, image3, image4, image5, image6];

interface LobbyProps {
    sessionId: string;
    myPlayerId: number;
    players: Player[];
    started: boolean;
    startRoom: (boardId: number, initialTokens: number, themeIds: number[]) => void;
    changeVisibility: (publicSession: boolean) => void;
}

const INITIAL_THEMES: ThemeProps[] = [
    { id: '1', code: 'opt1', name: 'Opção 1', free: true, cost: 0 },
    { id: '2', code: 'opt2', name: 'Opção 2', free: false, cost: 10 },
    { id: '3', code: 'opt3', name: 'Opção 3', free: false, cost: 20 },
    { id: '4', code: 'opt4', name: 'Opção 4', free: false, cost: 30 },
    { id: '5', code: 'opt5', name: 'Opção 5', free: false, cost: 40 },
    { id: '6', code: 'opt6', name: 'Opção 6', free: false, cost: 50 }
];

export default function Lobby({ sessionId, myPlayerId, players, started, startRoom, changeVisibility }: LobbyProps) {

    const [sessionType, setSessionType] = useState<'publica'|'particular'>('publica');
    const [boardId, setBoardId] = useState<number>(0);
    const [initialTokens, setInitialTokens] = useState<number>(0);

    const [availableThemes, setAvailableThemes] = useState<Theme[]>([]);
    const [selectedThemes, setSelectedThemes] = useState<Theme[]>([]);

    useEffect(() => {
        setAvailableThemes(INITIAL_THEMES.map(props => new Theme(props)));
        setBoardId(1); 
        setInitialTokens(5);
    }, []);

    useEffect(() => {
        changeVisibility(sessionType === 'publica');
    }, [sessionType, changeVisibility]);

    // TODO Review... assume the first player in `players` array is the creator
    const isCreator = players[0]?.id === myPlayerId;

    const handleStart = () => {
        if(boardId && initialTokens && selectedThemes.length === 0) return;
        const themeIds = selectedThemes.map(t => Number(t.id));
        startRoom(boardId, initialTokens, themeIds);
    }

    const addTheme = (theme: Theme) => {
        setAvailableThemes(prev => prev.filter(t => t.id !== theme.id));
        setSelectedThemes(prev => [...prev, theme]);
    };

    const removeTheme = (theme: Theme) => {
        setSelectedThemes(prev => prev.filter(t => t.id !== theme.id));
        setAvailableThemes(prev => [...prev, theme]);
    };
 
    return (
        <section className={styles.dashboard}>
            <header>
                <h2>Lobby</h2>
            </header>

            <section className={styles.lobby}>
                <form onSubmit={e => e.preventDefault()}>
                    <input
                        type="text"
                        placeholder="Session ID"
                        value={sessionId}
                        readOnly
                    />

                    <select
                        value={sessionType}
                        onChange={e => setSessionType(e.target.value as 'publica' | 'particular')}
                    >
                        <option value="publica">Pública</option>
                        <option value="particular">Particular</option>
                    </select>

                    <input
                        type="number"
                        placeholder="Board ID"
                        value={boardId}
                        onChange={e => setBoardId(Number(e.target.value))}
                    />

                    <input
                        type="number"
                        placeholder="Initial Tokens"
                        value={initialTokens}
                        onChange={e => setInitialTokens(Number(e.target.value))}
                    />

                    <div className={styles.themeList}>
                        <select multiple size={6}>
                            {availableThemes.map(theme => (
                                <option
                                    key={theme.id}
                                    value={theme.id}
                                    onClick={() => addTheme(theme)}
                                >
                                    {theme.name}
                                </option>
                            ))}
                        </select>

                        <select multiple size={6}>
                            {selectedThemes.map(theme => (
                                <option
                                    key={theme.id}
                                    value={theme.id}
                                    onClick={() => removeTheme(theme)}
                                >
                                    {theme.name}
                                </option>
                            ))}
                        </select>
                    </div>
                </form>

                <ul>
                    {players.map((p, idx) => (
                        <li key={p.id}>
                            <img
                                src={images[idx % images.length]}
                                alt={`Avatar de ${p.name}`}
                                width={80}
                                height={80}
                            />
                            <p>
                                {p.name} {p.id === myPlayerId && '(Você)'}{' '}
                                {p.id === players[0]?.id && '🛡️'}
                            </p>
                            <button className="secondary-btn">Na Sala</button>
                        </li>
                    ))}
                </ul>
            </section>

            <footer className={styles.actions}>
                <button
                    onClick={() => window.history.back()}
                    className="secondary-btn"
                >
                    Voltar
                </button>
                <button
                    onClick={handleStart}
                    disabled={!isCreator || started || selectedThemes.length === 0}
                    className="btn"
                >
                    {started ? 'Partida Iniciada' : 'Iniciar Partida'}
                </button>
            </footer>
        </section>
    );
}

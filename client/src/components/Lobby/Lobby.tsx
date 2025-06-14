import { useEffect, useState } from 'react';
import { Player } from '../../models/Player';
import { Theme } from '../../models/Theme';
import styles from './Lobby.module.scss';

import image1 from '../../assets/avatar/avatar1.png';
import image2 from '../../assets/avatar/avatar2.png';
import image3 from '../../assets/avatar/avatar3.png';
import image4 from '../../assets/avatar/avatar4.png';
import image5 from '../../assets/avatar/avatar5.png';
import image6 from '../../assets/avatar/avatar6.png';
import { Copy, CopyCheck } from 'lucide-react';
import React from 'react';
import { usePlayer } from '../../hooks/data/usePlayer';
import { useTheme } from '../../hooks/data/useTheme';
const images = [image1, image2, image3, image4, image5, image6];

interface LobbyProps {
    sessionId: string;
    myPlayerId: number;
    players: Player[];
    started: boolean;
    hostId: number;
    startRoom: (boardId: number, initialTokens: number, themeIds: number[]) => void;
    changeVisibility: (publicSession: boolean) => void;
}


export default function Lobby({ sessionId, myPlayerId, players, started, hostId, startRoom, changeVisibility }: LobbyProps) {
    const { player, fetchPlayer, loading: playerLoading, error: playerError } = usePlayer(myPlayerId);
    const { themes, fetchThemes, loading: themesLoading, error: themesError } = useTheme();
    
    const [copied, setCopied] = React.useState(false);
    const [sessionType, setSessionType] = useState<'publica' | 'particular'>('particular');
    const [boardId, setBoardId] = useState<number>(0);
    const [initialTokens, setInitialTokens] = useState<number>(0);

    const [availableThemes, setAvailableThemes] = useState<Theme[]>([]);
    const [selectedThemes, setSelectedThemes] = useState<Theme[]>([]);

    useEffect(() => {
        fetchPlayer();
    }, [fetchPlayer]);

    useEffect(() => {
        fetchThemes();
    }, [fetchThemes]);

    useEffect(() => {
        if (!themesLoading && themes && player) {
            const meus = themes
                .filter(t => player.themeIds.includes(t.id))
                .map(t => new Theme(t));
            setAvailableThemes(meus);
        }
    }, [player, themes, themesLoading]);

    const handleCopy = () => {
        navigator.clipboard.writeText(sessionId);
        setCopied(true);
        setTimeout(() => setCopied(false), 2000);
    };

    useEffect(() => {
        changeVisibility(sessionType === 'publica');
    }, [sessionType, changeVisibility]);

    const isCreator = myPlayerId === hostId;

    const handleStart = () => {
        if (boardId && initialTokens && selectedThemes.length === 0) return;
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

    if (playerLoading || themesLoading) return <p>Carregando...</p>;
    if (playerError) return <p>Erro no jogador: {playerError}</p>;
    if (themesError) return <p>Erro nos temas: {themesError}</p>;

    return (
        <section className={styles.dashboard}>
            <header>
                <h2>Lobby</h2>
            </header>

            <section className={styles.lobby}>
                <form onSubmit={e => e.preventDefault()}>
                    
                    <button className={styles.copy} onClick={handleCopy}>
                        {sessionId}
                        <span className={styles.iconWrapper}>
                            <span className={`${styles.icon} ${copied ? styles.hidden : styles.visible}`}>
                            <Copy size={16} />
                            </span>
                            <span className={`${styles.icon} ${copied ? styles.visible : styles.hidden}`}>
                            <CopyCheck size={16} />
                            </span>
                        </span>
                    </button>
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
                            </p>
                            {p.id === hostId ? 
                                <p>Host</p> : <p>Na Sala</p>
                            }
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

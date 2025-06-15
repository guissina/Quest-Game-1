import { Badge } from 'lucide-react';
import { Player } from '../../models/Player';
import { PlayerState } from '../../models/PlayerState';
import image1 from '../../assets/avatar/avatar1.png';
import styles from './PlayerCard.module.scss';

interface PlayerCardProps {
    player: Player;
    state: PlayerState;
}

export default function PlayerCard({ player, state }: PlayerCardProps) {
    return (
        <div className={`${styles.card} ${state.isCurrentTurn ? styles.current : ''}`}>
            
            <img src={image1} alt={player.name} className={styles.avatar} />
            
            <span className={styles.name}>{player.name}</span>
            
            <div className={styles.tokens}>
                {state.tokens.map((t, i) => (
                    <div key={i} className={styles.coin}>
                        <Badge size={32} className={styles.badge} />
                        <span className={styles.coinValue}>{t}</span>
                    </div>
                ))}
            </div>
        </div>
    );
}

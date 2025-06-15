import { Badge } from 'lucide-react';
import { Player } from '../../models/Player';
import { PlayerState } from '../../models/PlayerState';
import styles from './PlayerCard.module.scss';
import { getAvatarUrl } from '../../utils/avatar';

interface PlayerCardProps {
    player: Player;
    state: PlayerState;
}

export default function PlayerCard({ player, state }: PlayerCardProps) {
    return (
        <div className={`${styles.card} ${state.isCurrentTurn ? styles.current : ''}`}>
            
            <img src={getAvatarUrl(player.avatarIndex)} alt={player.name} className={styles.avatar} />
            
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

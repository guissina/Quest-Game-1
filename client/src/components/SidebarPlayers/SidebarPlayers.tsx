import { Player } from '../../models/Player';
import { AbilityType, PlayerState } from '../../models/PlayerState';
import PlayerCard from '../PlayerCard/PlayerCard';
import styles from './SidebarPlayers.module.scss';

interface SidebarPlayersProps {
  players: Player[];
  playerStates: PlayerState[];
  onConfirmMove: (steps: number) => void;
  onUseAbility: (ability: AbilityType) => void;
}

export default function SidebarPlayers({
  players,
  playerStates,
}: SidebarPlayersProps) {
  return (
    <aside className={styles.sidebar}>
      {players.map((p) => {
        const ps = playerStates.find((s) => s.playerId === p.id)!;
        return (
          <PlayerCard
            key={p.id}
            player={p}
            state={ps}
            showAbilities={false}
            onConfirmMove={null}
            onUseAbility={null}
          />
        );
      })}
    </aside>
  );
}

import { Badge, Shield, ShieldCheck } from 'lucide-react';
import { Player } from '../../models/Player';
import { AbilityType, PlayerState } from '../../models/PlayerState';
import styles from './PlayerCard.module.scss';
import { getAvatarUrl } from '../../utils/avatar';

interface PlayerCardProps {
  player: Player;
  state: PlayerState;
  showAbilities: boolean;
  onConfirmMove: ((steps: number) => void) | null;
  onUseAbility: ((ability: AbilityType) => void) | null;
}

type LabelMapType =
  | 'ROLL_DICE'
  | 'FREE_MOVEMENT'
  | 'SKIP_OPPONENT_TURN'
  | 'RETURN_TILE'
  | 'TOKEN_THEFT'
  | 'BLOCK_TILE'
  | 'REVERSE_MOVEMENT'
  | 'SHUFFLE_CARDS';

const labelMap: Record<LabelMapType, string> = {
  ROLL_DICE: 'ROLAR DADO',
  FREE_MOVEMENT: 'MOVIMENTO LIVRE',
  SKIP_OPPONENT_TURN: 'PULAR OPONENTE',
  RETURN_TILE: 'MANDAR DE VOLTA',
  TOKEN_THEFT: 'ROUBAR TOKEN',
  BLOCK_TILE: 'BLOQUEAR CASA',
  REVERSE_MOVEMENT: 'REVERTER MOVIMENTO',
  SHUFFLE_CARDS: 'EMBARALHAR CARTAS',
};

export default function PlayerCard({
  player,
  state,
  showAbilities,
  onConfirmMove,
  onUseAbility,
}: PlayerCardProps) {
  return (
    <div
      className={`${styles.card} ${
        onUseAbility === null ? styles.oponent : ''
      } ${state.isCurrentTurn ? styles.current : ''}`}
    >
      <div className={styles.info}>
        <div className={styles.data}>
          <img
            src={getAvatarUrl(player.avatarIndex)}
            alt={player.name}
            className={styles.avatar}
          />

          <span className={styles.name}>{player.name}</span>
        </div>

        <div className={styles.tokens}>
          {state.tokens.map((t, i) => (
            <div
              onClick={() => {
                if (onConfirmMove !== null) onConfirmMove(t);
              }}
              key={i}
              className={styles.coin}
            >
              <Badge size={32} className={styles.badge} />
              <span className={styles.coinValue}>{t}</span>
            </div>
          ))}
        </div>
      </div>
      {showAbilities && (
        <div className={styles.abilities}>
          {state.getAllAbilities().map((a) => {
            const active = state.isAbilityActive(a);
            return (
              <button
                key={a}
                className={`${styles.abilityBtn} secondary-btn`}
                onClick={() => {
                  if (onUseAbility !== null) onUseAbility(a);
                }}
                title={active ? 'Ativa' : 'Inativa'}
              >
                <span className={styles.abilityName}>{labelMap[a] ?? a}</span>
                <span
                  className={`${
                    active ? styles.active : styles.inactive
                  } secondary-btn`}
                >
                  <span>{active ? <ShieldCheck /> : <Shield />}</span>
                </span>
              </button>
            );
          })}
        </div>
      )}
    </div>
  );
}

import styles from './SidebarActions.module.scss';
import { AbilityType, PlayerState } from '../../models/PlayerState';

interface SidebarActionsProps {
  myPlayerId: number;
  myState: PlayerState;
  currentPlayerId?: number;
  onConfirmMove: (steps: number) => void;
  onUseAbility: (ability: AbilityType) => void;
}

export default function SidebarActions({
  myState,
  currentPlayerId,
  myPlayerId,
  onConfirmMove,
  onUseAbility,
}: SidebarActionsProps) {
  const isMyTurn = currentPlayerId === myPlayerId;
  if (!isMyTurn || myState.pendingQuestion) return null;

  return (
    <>
      <div className={styles.section}>
        <h3>Selecione movimento</h3>
        <div className={styles.moveTokens}>
          {myState.tokens.map((t) => (
            <button
              key={t}
              className={styles.moveToken}
              onClick={() => onConfirmMove(t)}
            >
              {t}
            </button>
          ))}
        </div>
      </div>

      <div className={styles.section}>
        <h3>Habilidades</h3>
        <div className={styles.abilities}>
          {myState.getAllAbilities().map((a) => {
            const active = myState.isAbilityActive(a);
            return (
              <button
                key={a}
                className={`${styles.abilityBtn} ${
                  active ? styles.active : styles.inactive
                }`}
                onClick={() => onUseAbility(a)}
                title={active ? 'Ativa' : 'Inativa'}
              >
                {a} <span className={styles.status}>{active ? '✓' : '○'}</span>
              </button>
            );
          })}
        </div>
      </div>
    </>
  );
}

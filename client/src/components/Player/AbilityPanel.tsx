import { PlayerState, AbilityType } from '../../models/PlayerState';
import './AbilityPanel.scss';

interface AbilityPanelProps {
    playerState: PlayerState;
    onUseAbility: (ability: AbilityType) => void;
    disabled?: boolean;
}

const ABILITY_DESCRIPTIONS: Record<AbilityType, string> = {
    [AbilityType.FREE_MOVEMENT]: "Movimento Livre",
    [AbilityType.ROLL_DICE]: "Rolar Dado",
    [AbilityType.SKIP_OPPONENT_TURN]: "Pular Turno",
    [AbilityType.TOKEN_THEFT]: "Roubo de Peça",
    [AbilityType.RETURN_TILE]: "Retornar Casa",
    [AbilityType.BLOCK_TILE]: "Bloquear Casa",
    [AbilityType.REVERSE_MOVEMENT]: "Movimento Reverso",
    [AbilityType.SHUFFLE_CARDS]: "Embaralhar Cartas"
};

export default function AbilityPanel({ playerState, onUseAbility }: AbilityPanelProps) {
    // Mostrar TODAS as habilidades (não só as ativas)
    const playerAbilities: AbilityType[] = Object.values(AbilityType).filter(ability =>
        playerState.hasAbility(ability)
    );
    const activeAbilities = playerState.getAvailableAbilities();

    if (playerAbilities.length === 0) {
        return (
            <div className="ability-panel">
                <h3>Habilidades</h3>
                <p>Nenhuma habilidade coletada</p>
            </div>
        );
    }

    return (
        <div className="ability-panel">
            <h3>Habilidades ({activeAbilities.length}/{playerAbilities.length} ativas)</h3>
            <div className="ability-grid">
                {playerAbilities.map((ability: AbilityType) => {
                    const isActive = playerState.isAbilityActive(ability);


                    return (
                        <button
                            key={ability}
                            className={`ability-button ${isActive ? 'active' : 'inactive'} `}
                            onClick={() => onUseAbility(ability)}

                            title={`${ABILITY_DESCRIPTIONS[ability]} - ${isActive ? 'Ativa' : 'Inativa'}`}
                        >
                            <span className="ability-name">{ABILITY_DESCRIPTIONS[ability]}</span>
                            <span className="ability-status">{isActive ? '✓' : '○'}</span>
                        </button>
                    );
                })}
            </div>
        </div>
    );
}
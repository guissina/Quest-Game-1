package com.quest.enums;

public enum AbilityType {
    FREE_MOVEMENT("Movimento Livre"),
    ROLL_DICE("Rolar Dado"),
    SKIP_OPPONENT_TURN("Pular Turno do Oponente"),
    TOKEN_THEFT("Roubo de Peça"),
    RETURN_TILE("Retornar Casa"),
    BLOCK_TILE("Bloquear Casa"),
    REVERSE_MOVEMENT("Movimento Reverso"),
    SHUFFLE_CARDS("Embaralhar Cartas");

    private final String description;

    AbilityType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

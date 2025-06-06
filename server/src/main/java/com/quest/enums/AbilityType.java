package com.quest.enums;

public enum AbilityType {
    FREE_MOVEMENT("Movimento Livre"), // OK
    ROLL_DICE("Rolar Dado"), // OK
    SKIP_OPPONENT_TURN("Pular Turno do Oponente"), // OK
    TOKEN_THEFT("Roubo de Peça"), // Postponed
    RETURN_TILE("Retornar Casa"), // OK
    BLOCK_TILE("Bloquear Casa"), // TODO após a adição do boolean no TileState
    REVERSE_MOVEMENT("Movimento Reverso"), // Postponed
    SHUFFLE_CARDS("Embaralhar Cartas"), // TODO após a implementação do Shuffle inicial
    RESET_TOKENS("Resetar Peças"),
    CHANGE_OWN_QUESTION_THEME("Mudar Tema da Própria Pergunta"), // TODO
    CHANGE_OPPONENT_QUESTION_THEME("Mudar Tema da Pergunta do Oponente"); // TODO

    private final String description;

    AbilityType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

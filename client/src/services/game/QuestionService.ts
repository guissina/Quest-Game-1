import { Game } from "../../models/Game";

export class QuestionService {
    constructor() {}

    public process(
        aggregate: Game,
        playerId: string,
        steps: number,
        correct: boolean
    ) {
        if (correct)
            this.handleCorrect(aggregate, playerId);
        else
            this.handleIncorrect(aggregate, playerId, steps);
    }

    public verifyCanMove(aggregate: Game, playerId: string) {
        if (aggregate.mustAnswerBeforeMoving[playerId])
            throw new Error("Você precisa responder a pergunta antes de se mover");
    }

    private handleIncorrect(aggregate: Game, playerId: string, steps: number) {
        if (!aggregate.mustAnswerBeforeMoving[playerId]) {
            const player = aggregate.getPlayerById(playerId);
            player.consumeToken(steps);
        }
        aggregate.mustAnswerBeforeMoving[playerId] = true;
    }

    private handleCorrect(aggregate: Game, playerId: string) {
        aggregate.mustAnswerBeforeMoving[playerId] = false; // Desbloqueia o movimento
    }
}

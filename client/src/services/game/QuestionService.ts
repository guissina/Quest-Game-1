import { Game } from "../../models/Game";

export class QuestionService {
    constructor() {}

    process(
        aggregate: Game,
        playerId: string,
        steps: number,
        correct: boolean
    ) {
        if (!correct) {
            const player = aggregate.players.find(p => p.id === playerId)!;
            if (!aggregate.mustAnswerBeforeMoving[playerId])
                player.consumeToken(steps);
            aggregate.mustAnswerBeforeMoving[playerId] = true;
            return;
        }
        aggregate.mustAnswerBeforeMoving[playerId] = false;
    }
}

import { Question } from "../../models/Question";
import { GameEngine } from "../../services/game/GameEngine";

export const useBoardFlow = (
    engine: GameEngine | null,
    onQuestionNeeded: (question: Question, steps: number) => void
) => {

    const moveBySteps = (steps: number) => {
        if (!engine) return;
        const playerId = engine.state.currentPlayer.id;
        
        const tiles = engine.state.aggregate.board.tiles;
        const originIndex = tiles.findIndex((t) =>
            t.players.some((p) => p.id === playerId)
        );
        const dest = tiles[(originIndex + steps) % tiles.length];

        engine.move(playerId, dest.id);

        if (dest.questionTheme?.questions.length)
            onQuestionNeeded(dest.questionTheme.questions[0], steps);
    };

    return { moveBySteps };
};

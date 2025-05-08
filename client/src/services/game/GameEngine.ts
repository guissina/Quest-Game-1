import { Game } from "../../models/Game";
import { Question } from "../../models/Question";
import { BoardManager } from "./BoardManager";
import { QuestionService } from "./QuestionService";
import { TurnManager } from "./TurnManager";

export class GameEngine {
    constructor(
        private aggregate: Game,
        private turnMgr: TurnManager,
        private boardMgr: BoardManager,
        private questionSvc: QuestionService
    ) {}

    public clone(): GameEngine {
        return new GameEngine(
            new Game({
                id: this.aggregate.id,
                board: this.aggregate.board,
                players: this.aggregate.players,
                mustAnswerBeforeMoving: this.aggregate.mustAnswerBeforeMoving,
            }),
            this.turnMgr,
            this.boardMgr,
            this.questionSvc
        );
    }

    public seed() {
        this.boardMgr.seed(this.aggregate);
    }

    public move(playerId: string, tileId: string) {
        this.turnMgr.verifyTurn(playerId, this.aggregate.players);
        this.questionSvc.verifyCanMove(this.aggregate, playerId);
        this.boardMgr.moveTo(this.aggregate, playerId, tileId);
    }
  
    public answer(playerId: string, steps: number, correct: boolean) {
        this.turnMgr.verifyTurn(playerId, this.aggregate.players);
        this.questionSvc.process(this.aggregate, playerId, steps, correct);
        this.turnMgr.next(this.aggregate.players);
    }

    public get state() {
        return {
            aggregate: this.aggregate,
            currentPlayer: this.turnMgr.getCurrentPlayer(
                this.aggregate.players
            ),
        };
    }
}

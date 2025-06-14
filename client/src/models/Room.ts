export interface RoomProps {
    sessionId: string;
    hostId: number;
    playerCount: number;
    started: boolean;
    publicSession: boolean;
}

export class Room {
    public readonly sessionId: string;
    public readonly hostId: number;
    public readonly playerCount: number;
    public readonly started: boolean;
    public readonly publicSession: boolean;

    constructor(room: RoomProps) {
        this.sessionId = room.sessionId;
        this.hostId = room.hostId;
        this.playerCount = room.playerCount;
        this.started = room.started;
        this.publicSession = room.publicSession;
    }
}


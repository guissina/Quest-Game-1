import { useRoomWebSocket } from "../hooks/useRoomWebSocket";
import LobbyPage from "./LobbyPage";
import GamePage from "./GamePage";

export default function SessionPage() {
    const { sessionId, players, started, createRoom, joinRoom, startRoom } = useRoomWebSocket();

    if (!started) {
        return (
            <LobbyPage
                sessionId={sessionId}
                players={players}
                started={started}
                createRoom={createRoom}
                joinRoom={joinRoom}
                startRoom={startRoom}
            />
        );
    }

    return <GamePage sessionId={sessionId!} myPlayerId={Number(localStorage.getItem('userId'))} players={players} />;
}

import { useEffect } from "react";
import { Navigate, useParams } from "react-router-dom";
import { useRoomWebSocket } from "../hooks/useRoomWebSocket";
import LobbyPage from "./LobbyPage";
import GamePage from "./GamePage";
import { useAuth } from "../contexts/AuthContext";

export default function SessionPage() {
    const { user } = useAuth();
    const { id: routeId } = useParams<{ id: string }>();
    const { ready, sessionId, players, started, joinRoom, leaveRoom, startRoom } = useRoomWebSocket();

    useEffect(() => {
        if (ready && routeId && sessionId !== routeId)
            joinRoom(routeId);

        return () => {
            if (sessionId) leaveRoom();
        };
    }, [ready, routeId, sessionId, joinRoom, leaveRoom]);

    if (!user) return <Navigate to="/" replace />;
    if (!ready) return <p>Connecting to session…</p>;
    if (routeId && !sessionId) return <p>Joining session “{routeId}”…</p>;
    if (!sessionId) return <Navigate to="/" replace />;

    return !started ? (
        <LobbyPage
            sessionId={sessionId}
            myPlayerId={user.id}
            started={started}
            players={players}
            startRoom={startRoom}
        />
    ) : (
        <GamePage sessionId={sessionId} myPlayerId={user.id} players={players} />
    );
}

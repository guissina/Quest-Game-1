import { useCallback, useEffect, useState } from "react";
import { useAuth } from "../contexts/AuthContext";
import { useWebSocketClient, useWebSocketStatus } from "../contexts/WebSocketContext";
import { PlayerProps } from "../models/Player";

export function useRoomWebSocket() {
    const { user } = useAuth();
    const myPlayerId = user?.id ?? null;

    const client = useWebSocketClient();
    const isConnected = useWebSocketStatus();

    const [sessionId, setSessionId] = useState<string | null>(null);
    const [players, setPlayers] = useState<PlayerProps[]>([]);
    const [started, setStarted] = useState(false);

    const ready = !!client && isConnected && !!myPlayerId;

    useEffect(() => {
        if (!ready) return;

        const sub = client!.subscribe("/user/queue/room-created", (msg) => {
            const { sessionId: newId } = JSON.parse(msg.body);
            setSessionId(newId);
        });

        return () => sub.unsubscribe();
    }, [client, ready]);

    useEffect(() => {
        if (!ready || !sessionId) return;

        const sub = client!.subscribe(`/topic/room/${sessionId}/state`, (msg) => {
        const { players: list, started: isStarted } = JSON.parse(msg.body);
            setPlayers(list);
            setStarted(isStarted);
            console.log("[ROOM STATE]", { sessionId, list, isStarted });
        });

        return () => sub.unsubscribe();
    }, [client, ready, sessionId]);

    useEffect(() => {
        if (!ready || !sessionId) return;

        client!.publish({
            destination: "/app/room/join",
            body: JSON.stringify({ sessionId, playerId: myPlayerId })
        });
    }, [client, ready, sessionId, myPlayerId]);

    const createRoom = useCallback(() => {
        if (!ready) {
            console.warn("createRoom: not ready (no player or websocket)");
            return;
        }
        client!.publish({
            destination: "/app/room/create",
            body: JSON.stringify({ creatorPlayerId: myPlayerId })
        });
    }, [client, ready, myPlayerId]);

    const joinRoom = useCallback((sessionId: string) => {
        if (!ready) {
            console.warn("joinRoom: not ready (no player or websocket)");
            return;
        }
        setSessionId(sessionId);
    }, [ready]);

    const leaveRoom = useCallback(() => {
        if (!client || !isConnected || !sessionId) return;
        client.publish({
            destination: "/app/room/leave",
            body: JSON.stringify({ sessionId, playerId: myPlayerId }),
        });
    }, [client, isConnected, sessionId, myPlayerId]);

    const startRoom = useCallback((boardId: number, initialTokens: number, themeIds: number[]) => {
        if (!ready || !sessionId) {
            console.warn("startRoom: not ready or missing session");
            return;
        }
        client!.publish({
            destination: "/app/room/start",
            body: JSON.stringify({ sessionId, boardId, initialTokens, themeIds })
        });
    }, [client, ready, sessionId]);

    return {
        ready,
        sessionId,
        players,
        started,
        createRoom,
        joinRoom,
        leaveRoom,
        startRoom,
    };
}

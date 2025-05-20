import { useCallback, useEffect, useRef, useState } from "react";
import { useWebSocketClient, useWebSocketStatus } from "../contexts/WebSocketContext";
import { PlayerProps } from "../models/Player";

export function useRoomWebSocket() {
    const client = useWebSocketClient();
    const isConnected = useWebSocketStatus();

    const playerRef = useRef<number | null>(null);
    const [sessionId, setSessionId] = useState<string | null>(null);
    const [players, setPlayers] = useState<PlayerProps[]>([]);
    const [started, setStarted] = useState(false);

    useEffect(() => {
        if (!client || !isConnected) return;

        const sub = client.subscribe("/user/queue/room-created", (msg) => {
            const { sessionId: newSessionId } = JSON.parse(msg.body) as {
                sessionId: string;
            };
            setSessionId(newSessionId);
        });

        return () => sub.unsubscribe();
    }, [client, isConnected]);

    useEffect(() => {
        if (!client || !isConnected || !sessionId) return;

        const topic = `/topic/room/${sessionId}/state`;
        const sub = client.subscribe(topic, (msg) => {
            const { players: list, started: isStarted } = JSON.parse(msg.body) as {
                players: PlayerProps[];
                started: boolean;
            };

            setPlayers(list);
            setStarted(isStarted);
            console.log("[ROOM STATE]", { sessionId, list, isStarted });
        });

        return () => sub.unsubscribe();
    }, [client, isConnected, sessionId]);

    useEffect(() => {
        if (!client || !isConnected || !sessionId) return;
        const playerId = playerRef.current;
        if (playerId == null) return;

        client.publish({
            destination: "/app/room/join",
            body: JSON.stringify({ sessionId, playerId }),
        });
        playerRef.current = null;
    }, [client, isConnected, sessionId]);

    const createRoom = useCallback((playerId: number) => {
        if (!client || !isConnected) {
            console.warn("WS not ready");
            return;
        }
        playerRef.current = playerId;

        client.publish({
            destination: "/app/room/create",
            body: JSON.stringify({ creatorPlayerId: playerId }),
        }); 
    }, [client, isConnected]);

    const joinRoom = useCallback((id: string, playerId: number) => {
        if (!client || !isConnected) return;
        playerRef.current = playerId;
        setSessionId(id);
    }, [client, isConnected]);

    const startRoom = useCallback((boardId: number, initialTokens: number) => {
        if (!client || !isConnected || !sessionId) return;

        client.publish({
            destination: "/app/room/start",
            body: JSON.stringify({ sessionId, boardId, initialTokens }),
        });
    }, [client, isConnected, sessionId]);

    return {
        sessionId,
        players,
        started,
        createRoom,
        joinRoom,
        startRoom,
    };
}

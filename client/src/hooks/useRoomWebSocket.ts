import { useEffect, useState } from "react";
import { useWebSocketClient, useWebSocketStatus } from "../contexts/WebSocketContext";
import { PlayerProps } from "../models/Player";

export function useRoomWebSocket() {
    const client = useWebSocketClient();
    const isConnected = useWebSocketStatus();

    const [sessionId, setSessionId] = useState<string | null>(null);
    const [players, setPlayers] = useState<PlayerProps[]>([]);
    const [started, setStarted] = useState(false);

    useEffect(() => {
        if (!client || !isConnected) return;

        const sub = client.subscribe("/user/queue/room-created", ({ body }) => {
            const { sessionId } = JSON.parse(body) as { sessionId: string };
            setSessionId(sessionId);
        });

        return () => sub.unsubscribe();
    }, [client, isConnected]);

    useEffect(() => {
        if (!client || !isConnected || !sessionId) return;

        const subPlayers = client.subscribe(`/topic/room/${sessionId}/players`, ({ body }) => {
            const { players: list, started: isStarted } = JSON.parse(body) as { 
                players: PlayerProps[]; started: boolean 
            };
            setPlayers(list);
            setStarted(isStarted);
        });

        const subStart = client.subscribe(`/topic/room/${sessionId}/start`, () => {
            setStarted(true);
        });

        return () => {
            subPlayers.unsubscribe();
            subStart.unsubscribe();
        };
    }, [client, isConnected, sessionId]);

    const createRoom = () => {
        if (!client || !isConnected) {
            console.warn("WebSocket não conectado ainda");
            return;
        }
        client.publish({ destination: "/app/room/create", body: "{}" });
    };

    const joinRoom = (id: string, playerId: number) => {
        if (!client || !isConnected) return;
        setSessionId(id);

        client.publish({
            destination: "/app/room/join",
            body: JSON.stringify({ sessionId: id, playerId }),
        });
    };

    const startRoom = (boardId: number, initialTokens: number) => {
        if (!client || !isConnected || !sessionId) return;

        client.publish({
            destination: "/app/room/start",
            body: JSON.stringify({ sessionId, boardId, initialTokens }),
        });
    };

    return {
        sessionId,
        players,
        started,
        createRoom,
        joinRoom,
        startRoom,
        isConnected,
    };
}

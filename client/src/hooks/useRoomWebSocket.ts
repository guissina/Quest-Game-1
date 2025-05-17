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

        const sub = client.subscribe("/user/queue/room-created", ({ body }) => {
            const { sessionId } = JSON.parse(body) as { sessionId: string };
            setSessionId(sessionId);

            if (playerRef.current) {
                client.publish({
                    destination: "/app/room/join",
                    body: JSON.stringify({
                        sessionId: sessionId,
                        playerId: playerRef.current,
                    }),
                });
                playerRef.current = null;
            }
        });

        return () => sub.unsubscribe();
    }, [client, isConnected]);

    useEffect(() => {
        if (!client || !isConnected || !sessionId) return;

        const dest = `/topic/room/${sessionId}/state`;
        const sub = client.subscribe(dest, ({ body }) => {
            const { players: list, started: isStarted, closed } = JSON.parse(body) as {
                players: PlayerProps[];
                started: boolean;
                closed: boolean; // TODO Limpar quando fechar a sala
            };

            setPlayers(list);
            setStarted(isStarted);

            // Log completo do estado da sala
            console.log("[ROOM STATE]", {
                sessionId,
                players: list,
                started: isStarted,
                closed,
            });
        });

        return () => sub.unsubscribe();
    }, [client, isConnected, sessionId]);

    const createRoom = useCallback((playerId: number) => {
        if (!client || !isConnected) return console.warn("WebSocket not ready");
        playerRef.current = playerId;
        client.publish({ 
            destination: "/app/room/create",
            body: JSON.stringify({ creatorPlayerId: playerId }),
        });
    }, [client, isConnected]);

    const joinRoom = useCallback((id: string, playerId: number) => {
        if (!client || !isConnected) return;
        setSessionId(id);
        client.publish({
            destination: "/app/room/join",
            body: JSON.stringify({ sessionId: id, playerId }),
        });
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

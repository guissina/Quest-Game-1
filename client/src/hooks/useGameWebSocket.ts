import { useCallback, useEffect, useState } from "react";
import { useWebSocketClient, useWebSocketStatus } from "../contexts/WebSocketContext";
import { Game, GameProps } from "../models/Game";

export function useGameWebSocket(sessionId: string | null) {
    const client = useWebSocketClient();
    const isConnected = useWebSocketStatus();

    const [gameState, setGameState] = useState<Game | null>(null); // TODO Tirar o any

    useEffect(() => {
        if (!client || !isConnected || !sessionId) return;

        const dest = `/topic/game/${sessionId}/state`;
        const sub = client.subscribe(dest, ({ body }) => {
            const state = JSON.parse(body) as GameProps;
            setGameState(new Game(state));
            console.log("[GAME STATE]", state);
        });

        client.publish({
            destination: `/app/game/${sessionId}/init`,
            body: JSON.stringify({ sessionId }),
        });

        return () => sub.unsubscribe();
    }, [client, isConnected, sessionId]);

    useEffect(() => {
        if (!client || !isConnected) return;

        const privateDest = "/user/queue/game-state";
        const sub = client.subscribe(privateDest, ({ body }) => {
            const state = JSON.parse(body) as GameProps;
            setGameState(new Game(state));
            console.log("[GAME STATE SNAPSHOT]", state);
        });

        return () => sub.unsubscribe();
    }, [client, isConnected]);

    const fetchGameState = useCallback(() => {
        if (!client || !isConnected || !sessionId) return;

        client.publish({
            destination: `/app/game/${sessionId}/state`,
            body: JSON.stringify({ sessionId }),
        });
    }, [client, isConnected, sessionId]);

    const movePlayer = useCallback((playerId: number, tileId: number) => {
        if (!client || !isConnected || !sessionId) return;

        client.publish({
            destination: `/app/game/${sessionId}/move`,
            body: JSON.stringify({ playerId, tileId }),
        });
    }, [client, isConnected, sessionId]);

    const answerQuestion = useCallback(
        (playerId: number, questionId: number, selectedOptionId: number, steps: number) => {

        if (!client || !isConnected || !sessionId) return;
            
        client.publish({
            destination: `/app/game/${sessionId}/answer`,
            body: JSON.stringify({ playerId, questionId, selectedOptionId, steps }),
        });
    }, [client, isConnected, sessionId]);

    useEffect(() => {
        if (sessionId) fetchGameState();
    }, [sessionId, fetchGameState]);

    return {
        gameState,
        movePlayer,
        answerQuestion,
        fetchGameState
    };
}

import { useCallback, useEffect, useState } from "react";
import { useWebSocketClient, useWebSocketStatus } from "../contexts/WebSocketContext";
import { Game, GameProps, Timer } from "../models/Game";
import { AbilityType } from "../models/PlayerState";

export function useGameWebSocket(sessionId: string | null) {
    const client = useWebSocketClient();
    const isConnected = useWebSocketStatus();

    const [gameState, setGameState] = useState<Game | null>(null);
    const [tick, setTick] = useState<Timer | null>(null);

    useEffect(() => {
        if (!client || !isConnected || !sessionId) return;

        const sub = client.subscribe(`/topic/game/${sessionId}/state`, msg => {
            const state = JSON.parse(msg.body) as GameProps;
            setGameState(new Game(state));
            console.log("[GAME STATE]", state);
        });
        return () => sub.unsubscribe();
    }, [client, isConnected, sessionId]);

    useEffect(() => {
        if (!client || !isConnected) return;

        const sub = client.subscribe("/user/queue/game-state",  msg => {
            const state = JSON.parse(msg.body) as GameProps;
            setGameState(new Game(state));
            console.log("[GAME STATE SNAPSHOT]", state);
        });

        return () => sub.unsubscribe();
    }, [client, isConnected]);

    useEffect(() => {
    if (!client || !isConnected) return;

        const sub = client.subscribe(`/topic/room/${sessionId}/timer`, msg => {
            const timer = JSON.parse(msg.body) as Timer;
            setTick(timer);
            console.log("[TIMER]: " + timer.timerType + ": " + timer.secondsLeft);
        });
        return () => sub.unsubscribe();
    }, [client, isConnected, sessionId]);

    const fetchGameState = useCallback(() => {
        if (!client || !isConnected || !sessionId) return;

        client.publish({
            destination: `/app/game/${sessionId}/state`,
            body: JSON.stringify({ sessionId }),
        });
    }, [client, isConnected, sessionId]);

    const movePlayer = useCallback((playerId: number, steps: number) => {
        if (!client || !isConnected || !sessionId) return;

        client.publish({
            destination: `/app/game/${sessionId}/move`,
            body: JSON.stringify({ playerId, steps }),
        });
    }, [client, isConnected, sessionId]);

    const drawQuestion = useCallback((playerId: number, themeId: number, steps: number) => {
        if (!client || !isConnected || !sessionId) return;

        client.publish({
            destination: `/app/game/${sessionId}/draw`,
            body: JSON.stringify({ playerId, themeId, steps }),
        });
    }, [client, isConnected, sessionId]);

    const answerQuestion = useCallback((playerId: number, questionId: number, selectedOptionId: number) => {
        if (!client || !isConnected || !sessionId) return;

        client.publish({
            destination: `/app/game/${sessionId}/answer`,
            body: JSON.stringify({ playerId, questionId, selectedOptionId }),
        });
    }, [client, isConnected, sessionId]);

    const useAbility = useCallback((playerId: number, abilityType: AbilityType) => {
        if (!client || !isConnected || !sessionId) return;

        client.publish({
            destination: `/app/game/${sessionId}/use-ability`,
            body: JSON.stringify({ playerId, abilityType }),
        });
    }, [client, isConnected, sessionId]);

    useEffect(() => {
        if (sessionId) fetchGameState();
    }, [sessionId, fetchGameState]);

    return {
        gameState,
        tick,
        movePlayer,
        drawQuestion,
        answerQuestion,
        fetchGameState,
        useAbility
    };
}

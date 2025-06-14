import { useCallback, useEffect, useRef, useState } from "react";
import { useAuth } from "../contexts/AuthContext";
import { useWebSocketClient, useWebSocketStatus } from "../contexts/WebSocketContext";
import { Player, PlayerProps } from "../models/Player";
import { Room, RoomProps } from "../models/Room";

export function useRoomWebSocket() {
    const { user } = useAuth();
    const myPlayerId = user?.id ?? null;

    const client = useWebSocketClient();
    const isConnected = useWebSocketStatus();
    const hasJoinedRef = useRef(false);

    const [sessionId, setSessionId] = useState<string | null>(null);
    const [players, setPlayers] = useState<Player[]>([]);
    const [started, setStarted] = useState(false);
    const [hostId, setHostId] = useState<number>(0);
    const [publicRooms, setPublicRooms] = useState<Room[]>([]);

    const ready = !!client && isConnected && !!myPlayerId;

    useEffect(() => {
        if (!ready) return;

        const sub = client!.subscribe("/user/queue/room-created", (msg) => {
            const { sessionId: newId } = JSON.parse(msg.body);
            setSessionId(newId);
            hasJoinedRef.current = false;
        });

        return () => sub.unsubscribe();
    }, [client, ready]);

    useEffect(() => {
        if (!ready) return;

        const sub = client!.subscribe("/user/queue/rooms/public", (msg) => {
            console.log("[PUBLIC ROOMS]", JSON.parse(msg.body));
            const roomsProps: RoomProps[] = JSON.parse(msg.body);
            const rooms = roomsProps.map((rp: RoomProps) => new Room(rp)); console.log(rooms)
            setPublicRooms(rooms);
        });

        return () => sub.unsubscribe();
    }, [client, ready]);

    useEffect(() => {
        if (!ready || !sessionId) return;

        const sub = client!.subscribe(`/topic/room/${sessionId}/state`, (msg) => {
            const { players: list, started: isStarted, hostId: hostId } = JSON.parse(msg.body);
            setPlayers(list.map((player: PlayerProps) => new Player(player)));
            setStarted(isStarted);
            setHostId(hostId);
            console.log("[GAME ROOM]", JSON.parse(msg.body));
        });
        
        hasJoinedRef.current = true;
        return () => sub.unsubscribe();
    }, [client, ready, sessionId]);

    useEffect(() => {
        if (!ready) return;

        const sub = client!.subscribe("/topic/rooms/public", (msg) => {
            const roomsProps: RoomProps[] = JSON.parse(msg.body);
            const rooms = roomsProps.map(rp => new Room(rp));
            setPublicRooms(rooms);
        });

        return () => sub.unsubscribe();
    }, [client, ready]);

    useEffect(() => {
        if (!ready || !sessionId) return;

        client!.publish({
            destination: "/app/room/join",
            body: JSON.stringify({ sessionId, playerId: myPlayerId })
        });
    }, [client, ready, sessionId, myPlayerId]);

    const createRoom = useCallback(() => {
        if (!ready) return;

        client!.publish({
            destination: "/app/room/create",
            body: JSON.stringify({ hostId: myPlayerId, publicSession: false })
        });
    }, [client, ready, myPlayerId]);

    const joinRoom = useCallback((sessionId: string) => {
        if (!ready) return;

        setSessionId(sessionId);
    }, [ready]);

    const listPublicRooms = useCallback(() => {
        if (!ready) return;

        client!.publish({
            destination: "/app/rooms/public",
            body: "{}"
        });
    }, [client, ready]);

    const leaveRoom = useCallback(() => {
        if (!client || !isConnected || !sessionId) return;

        client.publish({
            destination: "/app/room/leave",
            body: JSON.stringify({ sessionId, playerId: myPlayerId }),
        });
    }, [client, isConnected, sessionId, myPlayerId]);

    const startRoom = useCallback((boardId: number, initialTokens: number, themeIds: number[]) => {
        if (!ready || !sessionId) return;

        client!.publish({
            destination: "/app/room/start",
            body: JSON.stringify({ sessionId, boardId, initialTokens, themeIds })
        });
    }, [client, ready, sessionId]);

    const changeVisibility = useCallback((publicSession: boolean) => {
        if (!ready || !sessionId) return;

        client!.publish({
            destination: "/app/room/state",
            body: JSON.stringify({ sessionId, publicSession })
        });
    }, [client, ready, sessionId]);

    return {
        ready,
        sessionId,
        players,
        started,
        hostId,
        publicRooms,
        createRoom,
        joinRoom,
        listPublicRooms,
        leaveRoom,
        startRoom,
        changeVisibility
    };
}

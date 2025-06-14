import {
    createContext,
    ReactNode,
    useContext,
    useEffect,
    useRef,
    useState,
    useCallback
} from "react";
import { useAuth } from "./AuthContext";
import { useWebSocketClient, useWebSocketStatus } from "./WebSocketContext";
import { Player, PlayerProps } from "../models/Player";
import { Room, RoomProps } from "../models/Room";
import { useNavigate } from "react-router-dom";

interface RoomContextType {
    ready: boolean;
    sessionId: string | null;
    players: Player[];
    started: boolean;
    hostId: number;
    publicRooms: Room[];
    createRoom: () => void;
    joinRoom: (id: string) => void;
    listPublicRooms: () => void;
    leaveRoom: () => void;
    startRoom: (boardId: number, initialTokens: number, themeIds: number[]) => void;
    changeVisibility: (publicSession: boolean) => void;
}

const RoomContext = createContext<RoomContextType | undefined>(undefined);

export function RoomProvider({ children }: { children: ReactNode }) {
    const { user } = useAuth();
    const myPlayerId = user?.id ?? null;

    const navigate = useNavigate();

    const client = useWebSocketClient();
    const isConnected = useWebSocketStatus();
    const ready = !!client && isConnected && !!myPlayerId;
    const hasJoinedRef = useRef(false);

    const [sessionId, setSessionId] = useState<string | null>(null);
    const [players, setPlayers] = useState<Player[]>([]);
    const [started, setStarted] = useState(false);
    const [hostId, setHostId] = useState<number>(0);
    const [publicRooms, setPublicRooms] = useState<Room[]>([]);

    //
    // Room Create
    //
    useEffect(() => {
        if (!ready) return;

        const sub = client!.subscribe("/user/queue/room-created", (msg) => {
            const { sessionId: newId } = JSON.parse(msg.body);
            hasJoinedRef.current = false;
            joinRoom(newId);
            navigate(`/session/${newId}`)
        });
        return () => sub.unsubscribe();
    }, [client, ready]);

    //
    // Public Rooms
    //
    useEffect(() => {
        if (!ready) return;

        const sub = client!.subscribe("/topic/rooms/public", (msg) => {
            const roomsProps: RoomProps[] = JSON.parse(msg.body);
            setPublicRooms(roomsProps.map(rp => new Room(rp)));
        });
        return () => sub.unsubscribe();
    }, [client, ready]);

    //
    // Room State
    //
    useEffect(() => {
        if (!ready || !sessionId) return;

        const sub = client!.subscribe(`/topic/room/${sessionId}/state`, (msg) => {
            const { players: list, started: isStarted, hostId } = JSON.parse(msg.body);
            setPlayers(list.map((p: PlayerProps) => new Player(p)));
            setStarted(isStarted);
            setHostId(hostId);
        });
        return () => sub.unsubscribe();
    }, [client, ready, sessionId]);

    //
    // Actions
    //

    const createRoom = useCallback(() => {
        if (!ready) return;

        client!.publish({
            destination: "/app/room/create",
            body: JSON.stringify({ hostId: myPlayerId, publicSession: false })
        });
    }, [client, ready, myPlayerId]);

    const joinRoom = useCallback((newSessionId: string) => {
        if (!ready || hasJoinedRef.current) return;

        hasJoinedRef.current = true;
        setSessionId(newSessionId);
        client!.publish({
            destination: "/app/room/join",
             body: JSON.stringify({ sessionId: newSessionId, playerId: myPlayerId })
        });
        
        navigate(`/session/${newSessionId}`);
    }, [client, ready, myPlayerId]);

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
            body: JSON.stringify({ sessionId, playerId: myPlayerId })
        });
        hasJoinedRef.current = false;
        setSessionId(null);
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

    return (
        <RoomContext.Provider value={{
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
        }}>
            {children}
        </RoomContext.Provider>
    );
}

export function useRoom() {
    const context = useContext(RoomContext);
    if (!context) throw new Error("useRoom must be used within a RoomProvider");
    return context;
}
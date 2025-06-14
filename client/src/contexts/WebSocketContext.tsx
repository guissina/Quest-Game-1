import { createContext, useContext, useEffect, useRef, useState, ReactNode } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { useAuth } from "./AuthContext";

interface WSContextType {
    client: Client | null;
    isConnected: boolean;
}

const WebSocketContext = createContext<WSContextType>({ client: null, isConnected: false });

export const WebSocketProvider = ({ children }: { children: ReactNode }) => {
    const { user } = useAuth();
    const clientRef = useRef<Client | null>(null);
    const [isConnected, setIsConnected] = useState(false);

    useEffect(() => {
        if (!user) return;
        const wsUrl = import.meta.env.VITE_WEBSOCKET_URL ?? "http://localhost:8080/ws";
        const socket = new SockJS(wsUrl);

        const stomp = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,
            debug: (m) => console.debug("STOMP ▶️", m),
            onConnect: () => {
                console.log("🟢 STOMP conected");
                setIsConnected(true);
            },
            onStompError: (frame) => {
                console.error("🔴 STOMP Error:", frame.headers.message);
            },
        });

        stomp.activate();
        clientRef.current = stomp;

        return () => {
            stomp.deactivate();
            clientRef.current = null;
            setIsConnected(false);
        };
    }, [user]);

    return (
        <WebSocketContext.Provider value={{ client: clientRef.current, isConnected }}>
            {children}
        </WebSocketContext.Provider>
    );
};

export const useWebSocketClient = (): Client | null =>
    useContext(WebSocketContext).client;

export const useWebSocketStatus = (): boolean =>
    useContext(WebSocketContext).isConnected;

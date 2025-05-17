import React, { createContext, useContext, useEffect, useRef, useState, ReactNode } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

interface WSContextType {
    client: Client | null;
    isConnected: boolean;
}

const WebSocketContext = createContext<WSContextType>({
    client: null,
    isConnected: false,
});

export const WebSocketProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const clientRef = useRef<Client | null>(null);
    const [isConnected, setIsConnected] = useState(false);

    useEffect(() => {
        const socket = new SockJS("http://localhost:8080/ws");
        const stomp = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,
            debug: (m) => console.debug("STOMP ▶️", m),
            onConnect: () => {
                console.log("🟢 STOMP conectado");
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
    }, []);

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

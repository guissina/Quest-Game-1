import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { useEffect, useRef } from "react";

export function useWebSocket(onGreeting: (msg: string) => void) {
    const clientRef = useRef<Client | null>(null);

    useEffect(() => {
        const wsUrl = "http://localhost:8080/ws";
        const socket = new SockJS(wsUrl);

        const stomp = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,
            onConnect: () => {
                stomp.subscribe("/topic/greetings", (frame) => {
                    const body = JSON.parse(frame.body);
                    onGreeting(body.content);
                });

            },
            onStompError: (frame) => {
                console.error("Broker error:", frame.headers["message"]);
            },
        });

        clientRef.current = stomp;

        return () => {
            stomp.deactivate();
        };
    }, [onGreeting]);

    const sendHello = (name: string) => {
        clientRef.current?.publish({
            destination: "/app/hello",
            body: JSON.stringify({ name }),
        });
    };

    return { sendHello };
}

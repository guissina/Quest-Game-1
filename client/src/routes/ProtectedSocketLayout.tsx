import { Outlet } from "react-router-dom";
import { RoomProvider } from "../contexts/RoomContext";
import { WebSocketProvider } from "../contexts/WebSocketContext";

export default function ProtectedSocketLayout() {
    return (
        <WebSocketProvider>
            <RoomProvider>
                <Outlet />
            </RoomProvider>
        </WebSocketProvider>
    );
}

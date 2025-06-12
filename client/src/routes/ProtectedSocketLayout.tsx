import { Outlet } from "react-router-dom";
import { WebSocketProvider } from "../contexts/WebSocketContext";

export default function ProtectedSocketLayout() {
    return (
        <WebSocketProvider>
            <Outlet />
        </WebSocketProvider>
    );
}

import { ReactNode } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import { WebSocketProvider } from "../contexts/WebSocketContext";
import Home from "../page/Home/Home";
import Login from "../page/Login/Login";
import Register from "../page/Register/Register";
import Recover from "../page/Recover/Recover";
import Store from "../page/Store/Store";
import SessionPage from "../page/SessionPage";

function RequireAuth({ children }: { children: ReactNode }) {
    const { user } = useAuth();
    if (!user) return <Navigate to="/login" replace />;
    return children;
}

export default function AppRoutes() {
    return (
        <Routes>
            {/* públicas */}
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/recover" element={<Recover />} />

            {/* rotas protegidas */}
            <Route path="/store" element={<Store />} />
            <Route
                path="/game"
                element={
                    <RequireAuth>
                        <WebSocketProvider>
                            <SessionPage />
                        </WebSocketProvider>
                    </RequireAuth>
                }
            />

            <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
    );
}

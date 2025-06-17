import { Routes, Route, Navigate } from "react-router-dom";
import Home from "../page/Home/Home";
import Login from "../page/Login/Login";
import PlayerHub from "../page/PlayerHub/PlayerHub";
import Register from "../page/Register/Register";
import Recover from "../page/Recover/Recover";
import Store from "../page/Store/Store";
import SessionPage from "../page/Session/SessionPage";
import ProtectedSocketLayout from "./ProtectedSocketLayout";
import MyProfile from "../components/EditProfile/EditProfile";

export default function AppRoutes() {
    return (
        <Routes>
            {/* rotas publicas */}
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/recover" element={<Recover />} />
            <Route path="/edit-profile" element={<MyProfile />} />

            {/* rotas protegidas */}
            <Route path="/store" element={<Store />} />

            <Route element={<ProtectedSocketLayout />}>
                <Route path="/hub" element={<PlayerHub />} />
                <Route path="/session/:id" element={<SessionPage />} />
            </Route>

            <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
    );
}

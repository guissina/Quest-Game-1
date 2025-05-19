import { Route, Routes } from "react-router-dom";
import LoginPage from "./page/LoginPage";
import GamePage from "./page/GamePage";
import RegisterPage from "./page/RegisterPage";

export default function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<LoginPage />} />
            <Route path="/game" element={<GamePage />} />
            <Route path="/register" element={<RegisterPage />} />
        </Routes>
    );
}
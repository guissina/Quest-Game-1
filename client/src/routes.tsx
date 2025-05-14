import { Route, Routes } from "react-router-dom";
import LoginPage from "./page/LoginPage";
import GamePage from "./page/GamePage";

export default function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<LoginPage />} />
            <Route path="/game" element={<GamePage />} />
        </Routes>
    );
}
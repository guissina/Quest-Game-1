import { Routes, Route } from "react-router-dom";
import Home from "../page/Home/Home";
import Store from "../page/Store/Store";
import SessionPage from "../page/SessionPage";
import FormWrapper from "../components/FormWrapper/FormWrapper";
import FormLogin from "../components/FormLogin/FormLogin";
import FormRegister from "../components/FormRegister/FormRegister";
import FormRecover from "../components/FormRecover/FormRecover";
import { WebSocketProvider } from "../contexts/WebSocketContext";

function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={
                <FormWrapper>
                    <FormLogin />
                </FormWrapper>
            } />
            <Route path="/register" element={
                <FormWrapper>
                    <FormRegister />
                </FormWrapper>
            } />
            <Route path="/recover" element={
                <FormWrapper>
                    <FormRecover />
                </FormWrapper>
            } />
            <Route path="/store" element={<Store />} />
            <Route path="/game" element={
                <WebSocketProvider>
                    <SessionPage />
                </WebSocketProvider>
            } />
        </Routes>
    );
}

export default AppRoutes;
import { createContext, useState, useEffect, ReactNode, useContext } from "react";
import { authService } from "../services/authService";
import { Player } from "../models/Player";

interface AuthContextProps {
    user: Player | null;
    login: (email: string, password: string) => Promise<void>;
    register: (name: string, email: string, password: string, avatarIndex: number) => Promise<void>;
    logout: () => void;
}

const AuthContext = createContext<AuthContextProps>({} as any);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [user, setUser] = useState<Player | null>(null);

    useEffect(() => {
        const stored = authService.getCurrentUser();
        if (stored) setUser(stored);
    }, []);

    const login = async (email: string, password: string) => {
        const player = await authService.login(email, password);
        setUser(player);
    };

    const register = async (name: string, email: string, password: string, avatarIndex: number) => {
        const player = await authService.register(name, email, password, avatarIndex);
        setUser(player);
    };

    const logout = () => {
        authService.clearCurrentUser();
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{ user, login, register, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);

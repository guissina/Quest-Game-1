import styles from "./Formlogin.module.scss";
import { AtSign, LockKeyhole } from "lucide-react";
import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { useAuth } from "../../contexts/AuthContext";

interface LoginProps {
    email: string;
    password: string;
}

export default function FormLogin() {
    const { login, user } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (user) navigate("/hub");
    }, [user])

    const [formData, setFormData] = useState<LoginProps>({ email: "", password: "" });
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError(null);

        try {
            await login(formData.email, formData.password);
            navigate("/hub");
        } catch (err: any) {
            setError(err.message || "Falha ao efetuar login");
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className={"innerWrapper"}>
            <h1>Bem-vindo</h1>

            {error && <div className={"error"}>{error}</div>}

            <form onSubmit={handleSubmit}>
                <label htmlFor="email" className={"field"}>
                    <AtSign size={24} />
                    <input
                        id="email"
                        name="email"
                        type="email"
                        placeholder="seu@exemplo.com"
                        value={formData.email}
                        onChange={handleInputChange}
                        required
                        disabled={isLoading}
                    />
                </label>

                <label htmlFor="password" className={"field"}>
                    <LockKeyhole size={24} />
                    <input
                        id="password"
                        name="password"
                        type="password"
                        placeholder="••••••••"
                        value={formData.password}
                        onChange={handleInputChange}
                        required
                        disabled={isLoading}
                    />
                </label>

                <button className={"btn"} type="submit" disabled={isLoading}>
                    {isLoading ? "Carregando…" : "Entrar"}
                </button>
            </form>

            <div className={styles.links}>
                <p>
                    Não tem conta? <Link to="/register">Criar conta</Link>
                </p>
                <Link to="/recover">Redefinir minha senha</Link>
            </div>
        </div>
    );
}

import { AtSign, User } from "lucide-react";
import { useAuth } from "../../contexts/AuthContext";
import { useEffect, useState } from "react";
import "./EditProfile.scss";
import image1 from '../../assets/avatar/avatar1.png';
import image2 from '../../assets/avatar/avatar2.png';
import image3 from '../../assets/avatar/avatar3.png';
import image4 from '../../assets/avatar/avatar4.png';
import image5 from '../../assets/avatar/avatar5.png';
import image6 from '../../assets/avatar/avatar6.png';
import logo from '../../assets/QUEST.png';
import styles from './EditProfile.module.scss'
import { ArrowLeft } from "lucide-react";
import { useNavigate } from "react-router-dom";

const images = [image1, image2, image3, image4, image5, image6];

interface ProfileProps {
    name: string | undefined;
    email: string | undefined;
    id: number | undefined;
    password: string | undefined;
    themesIds?: number[];
    balance?: number;
    avatarIndex?: number;
}

export default function FormEditProfile() {
    const { user, update } = useAuth();

    const [formData, setFormData] = useState<ProfileProps>({
        name: user?.name,
        email: user?.email,
        id: user?.id,
        password: user?.password,
        themesIds: user?.themeIds,
        balance: user?.balance,
        avatarIndex: user?.avatarIndex,
    });

    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState<string | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selected, setSelected] = useState<number>(user?.avatarIndex ?? 0);

    const handleSelect = (index: number) => {
        setSelected(index);
        setFormData(prev => ({ ...prev, avatarIndex: index }));
        setIsModalOpen(false);
    };

    const navigate = useNavigate();

    const handleGoBack = () => {
        navigate("/hub");
    };

    useEffect(() => {
        if (user) {
            setFormData({
                name: user.name,
                email: user.email,
                id: user.id,
                password: user.password,
                themesIds: user.themeIds,
                balance: user.balance,
                avatarIndex: user.avatarIndex,
            });
            // sincroniza o selected pro valor padrão
            setSelected(user.avatarIndex);
        }
    }, [user]);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError(null);
        setSuccess(null);

        if (selected === null) {
            setError("Por favor, selecione um avatar");
            setIsLoading(false);
            return;
        }

        try {
            await update({
                id: formData.id!,
                name: formData.name!,
                email: formData.email!,
                password: formData.password || "",
                themeIds: formData.themesIds || user!.themeIds,
                balance: formData.balance ?? user!.balance,
                avatarIndex: selected,
            });
            setSuccess("Perfil atualizado com sucesso!");
            navigate("/hub");
        } catch (err) {
            setError(err instanceof Error ? err.message : "Erro desconhecido");
        } finally {
            setIsLoading(false);
        }
    };


    return (
        <div className="edit-profile-background">
            <div className="edit-profile-wrapper">
                <button className={styles.backButton} onClick={handleGoBack}>
                    <ArrowLeft size={20} /> Voltar
                </button>
                <img
                    src={logo}
                    alt="Logo Quest"
                    className="edit-profile-logo"
                />
                <h1>Editar Perfil</h1>

                <div className="edit-profile-avatar">
                    <img src={images[selected]} alt="Avatar" />
                    <button
                        type="button"
                        onClick={() => setIsModalOpen(true)}
                        className={`${styles.selectAvatar} btn`}
                        disabled={isLoading}
                        value={formData.avatarIndex}
                    >
                        Selecionar Avatar
                    </button>
                </div>
                {error && <div className={"error"}>{error}</div>}
                {success && <div className={"success"}>{success}</div>}
                <form onSubmit={handleSubmit} className="edit-profile-form">
                    {/* campo oculto para avatarIndex */}
                    <input
                        type="hidden"
                        name="avatarIndex"
                        value={formData.avatarIndex ?? ""}
                    />

                    <label htmlFor="name" className="edit-profile-field">
                        <User size={24} />
                        <input
                            id="name"
                            name="name"
                            type="text"
                            placeholder="Seu nome"
                            value={formData.name}
                            onChange={handleInputChange}
                            required
                            disabled={isLoading}
                        />
                    </label>
                    <label htmlFor="email" className="edit-profile-field">
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
                    <label htmlFor="password" className="edit-profile-field">
                        <User size={24} />
                        <input
                            id="password"
                            name="password"
                            type="password"
                            placeholder="Nova senha (opcional)"
                            value={formData.password}
                            onChange={handleInputChange}
                            disabled={isLoading}
                        />
                    </label>
                    <button
                        className="edit-profile-btn"
                        type="submit"
                        disabled={isLoading}
                    >
                        {isLoading ? "Salvando…" : "Salvar alterações"}
                    </button>
                </form>
                {isModalOpen && (
                    <div
                        className={styles.modalOverlay}
                        onClick={() => setIsModalOpen(false)}
                    >
                        <div
                            className={styles.modalContent}
                            onClick={e => e.stopPropagation()}
                        >
                            <h2>Selecione um avatar</h2>
                            <div className={styles.imageGrid}>
                                {images.map((img, index) => (
                                    <button
                                        key={index}
                                        type="button"
                                        onClick={() => handleSelect(index)}
                                        className={styles.imageButton}
                                        disabled={isLoading}
                                    >
                                        <img
                                            src={img}
                                            alt={`Avatar ${index + 1}`}
                                            width={140}
                                            height={140}
                                        />
                                    </button>
                                ))}
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}
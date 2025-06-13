import React, { useState } from 'react';
import { CircleUser } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import styles from './FormRegister.module.scss';

import image1 from '../../assets/avatar/avatar1.png';
import image2 from '../../assets/avatar/avatar2.png';
import image3 from '../../assets/avatar/avatar3.png';
import image4 from '../../assets/avatar/avatar4.png';
import image5 from '../../assets/avatar/avatar5.png';
import image6 from '../../assets/avatar/avatar6.png';

const images = [image1, image2, image3, image4, image5, image6];

interface PlayerData {
    name: string;
    email: string;
    password: string;
    avatarIndex?: number;
}

export default function FormRegister() {
    const { register } = useAuth();
    const navigate = useNavigate();

    const [selected, setSelected] = useState<number | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [formData, setFormData] = useState<PlayerData>({
        name: '',
        email: '',
        password: '',
        avatarIndex: undefined,
    });
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleSelect = (index: number) => {
        setSelected(index);
        setFormData(prev => ({ ...prev, avatarIndex: index }));
        setIsModalOpen(false);
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError(null);

        if (selected === null) {
            setError('Por favor, selecione um avatar');
            setIsLoading(false);
            return;
        }

        try {
            await register(
                formData.name,
                formData.email,
                formData.password,
                formData.avatarIndex || selected
            );
            navigate('/hub');
        } catch (err: any) {
            setError(err.message || 'Falha ao cadastrar');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="innerWrapper">
            <h1>Criar conta</h1>

            <form className={styles.form} onSubmit={handleSubmit}>
                {error && <div className="error">{error}</div>}

                {selected === null ? (
                    <CircleUser size={140} strokeWidth={1} />
                ) : (
                    <img
                        src={images[selected]}
                        alt={`Avatar ${selected + 1}`}
                        width={140}
                        height={140}
                        className={styles.avatarPreview}
                    />
                )}

                <button
                    type="button"
                    onClick={() => setIsModalOpen(true)}
                    className={`${styles.selectAvatar} btn`}
                    disabled={isLoading}
                >
                    Selecionar Avatar
                </button>

                <label htmlFor="name">
                    <input
                        id="name"
                        name="name"
                        type="text"
                        placeholder="Digite seu nome"
                        value={formData.name}
                        onChange={handleInputChange}
                        required
                        disabled={isLoading}
                    />
                </label>

                <label htmlFor="email">
                    <input
                        id="email"
                        name="email"
                        type="email"
                        placeholder="Digite seu e-mail"
                        value={formData.email}
                        onChange={handleInputChange}
                        required
                        disabled={isLoading}
                    />
                </label>

                <label htmlFor="password">
                    <input
                        id="password"
                        name="password"
                        type="password"
                        placeholder="Digite sua senha"
                        value={formData.password}
                        onChange={handleInputChange}
                        required
                        disabled={isLoading}
                    />
                </label>

                <button className="btn" type="submit" disabled={isLoading}>
                    {isLoading ? 'Cadastrando...' : 'Cadastrar'}
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
    );
}

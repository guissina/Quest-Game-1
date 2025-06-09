import { CircleUser } from 'lucide-react';
import styles from './FormRegister.module.scss';
import image1 from '../../assets/avatar/avatar1.png';
import image2 from '../../assets/avatar/avatar2.png';
import image3 from '../../assets/avatar/avatar3.png';
import image4 from '../../assets/avatar/avatar4.png';
import image5 from '../../assets/avatar/avatar5.png';
import image6 from '../../assets/avatar/avatar6.png';
import React, { useState } from 'react';
import { api, extractErrorMessage } from '../../services/api';

const images = [image1, image2, image3, image4, image5, image6];

interface PlayerData {
  name: string;
  email: string;
  password: string;
}

export default function FormRegister() {
  const [selected, setSelected] = React.useState<null | number>(null);
  const [isModalOpen, setIsModalOpen] = React.useState(false);
  const [formData, setFormData] = useState<PlayerData>({
    name: '',
    email: '',
    password: '',
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSelect = (index: number) => {
    setSelected(index);
    setFormData(prev => ({
      ...prev,
      avatar: index
    }));
    setIsModalOpen(false);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    setError(null);

    // Validar se avatar foi selecionado
    if (selected === null) {
      setError('Por favor, selecione um avatar');
      setIsLoading(false);
      return;
    }

    try {
      const response = await api.post('/players', formData);

      console.log('Jogador criado com sucesso:', response.data);

      // Redirecionar para login após sucesso

    } catch (error) {
      setError(extractErrorMessage(error));
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className={'innerWrapper'}>
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
        >
          Selecionar Avatar
        </button>
        <label htmlFor="name">
          <input
            type="text"
            name="name"
            id="name"
            placeholder="Digite seu nome"
            value={formData.name}
            onChange={handleInputChange}
            required
          />
        </label>
        <label htmlFor="email">
          <input
            type="email"
            name="email"
            id="email"
            placeholder="Digite seu e-mail"
            value={formData.email}
            onChange={handleInputChange}
            required
          />
        </label>
        <label htmlFor="password">
          <input
            type="password"
            name="password"
            id="password"
            placeholder="Digite sua senha"
            value={formData.password}
            onChange={handleInputChange}
            required
          />
        </label>
        <button className={'btn'} type="submit" disabled={isLoading}>
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
            onClick={(e) => e.stopPropagation()}
          >
            <h2>Selecione um avatar</h2>
            <div className={styles.imageGrid}>
              {images.map((img, index) => (
                <button
                  key={index}
                  onClick={() => handleSelect(index)}
                  className={styles.imageButton}
                >
                  <img
                    src={img}
                    width={140}
                    height={140}
                    alt={`Avatar ${index + 1}`}
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

import styles from './Formlogin.module.scss';
import { AtSign, LockKeyhole } from 'lucide-react';
import { Link } from 'react-router-dom';
import { useState } from 'react';
import { api } from '../../services/api';
import { extractErrorMessage } from '../../services/api';

interface LoginData {
  email: string;
  password: string;
}

export default function FormLogin() {
  const [formData, setFormData] = useState<LoginData>({
    email: '',
    password: '',
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

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

    try {
      await api.post('/login', formData);
    }
    catch (error) {
      const errorMessage = extractErrorMessage(error);
      setError(errorMessage);
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <>
      <div className={'innerWrapper'}>
        <h1>Bem-vindo</h1>
        {error && <div className="error">{error}</div>}
        <form onSubmit={handleSubmit}>
          <label htmlFor="email">
            <AtSign size={24} />
            <input id="email" name="email" type="email" placeholder="email" value={formData.email} onChange={handleInputChange} required />
          </label>

          <label htmlFor="password">
            <LockKeyhole size={24} />
            <input id="password" name="password" type="password" placeholder="password" value={formData.password} onChange={handleInputChange} required />
          </label>

          <button className="btn" type="submit" disabled={isLoading}>
            {isLoading ? 'Carregando...' : 'Entrar'}
          </button>
        </form>
      </div>
      <div className={styles.links}>
        <p>
          Não tem conta? <Link to="/register">Criar conta</Link>
        </p>

        <Link to="/recover">Redefinir minha senha.</Link>
      </div>
    </>
  );
}
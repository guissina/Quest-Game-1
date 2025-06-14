import styles from './Store.module.scss';
import back from '../../assets/store/go-back.png';
import logo from './../../assets/QUEST.png';
import { Link } from 'react-router-dom';
import { useTheme } from '../../hooks/data/useTheme';
import { usePlayer } from '../../hooks/data/usePlayer';
import StoreCard from '../../components/StoreCard/StoreCard';
import ProductCarroussel from '../../components/ProductCarroussel/ProductCarroussel';
import { useAuth } from '../../contexts/AuthContext';

export default function Store() {
  const { user } = useAuth();
  if (!user) return <p>Usuário não encontrado.</p>;

  const { player, decreasePlayerBalance, addPlayerBalance, addPlayerTheme } = usePlayer(user.id || 0);

  const { themes, loading, error } = useTheme();
  if (loading) return <p>Carregando lojas…</p>;
  if (error) return <p>Erro ao carregar: {error}</p>;
  if (themes.length === 0) return <p>Sem temas disponíveis.</p>;

  return (
    <div className={styles.store}>
      <header className={styles.header}>
        <Link to="/hub">
          <img src={back} alt="Página Anterior" />
          Voltar
        </Link>
        <img src={logo} alt="Logo" className={styles.logo} />
      </header>

      <section>
        <h1>Loja</h1>
        {player && (<StoreCard props={player} addBalance={addPlayerBalance} />)}
      </section>

      <ProductCarroussel props={themes} decreaseBalace={decreasePlayerBalance} addTheme={addPlayerTheme} />
    </div>
  );
}

import styles from './Store.module.scss';
import produto from '../../assets/store/produto.png';
import left from '../../assets/store/left-btn.png';
import right from '../../assets/store/right-btn.png';
import back from '../../assets/store/go-back.png';
import image1 from '../../assets/avatar/avatar1.png';
import image2 from '../../assets/avatar/avatar2.png';
import image3 from '../../assets/avatar/avatar3.png';
import image4 from '../../assets/avatar/avatar4.png';
import image5 from '../../assets/avatar/avatar5.png';
import image6 from '../../assets/avatar/avatar6.png';
import logo from './../../assets/QUEST.png';
import { BadgeDollarSign } from 'lucide-react';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useTheme } from '../../hooks/data/useTheme';
import { usePlayer } from '../../hooks/data/usePlayer';

const images = [image1, image2, image3, image4, image5, image6];


export default function Store() {
  const visibleCount = 3;
  const [startIndex, setStartIndex] = useState(0);

  const { player } = usePlayer("11");

  const { themes, loading, error } = useTheme();
  if (loading) return <p>Carregando lojas…</p>;
  if (error) return <p>Erro ao carregar: {error}</p>;
  if (themes.length === 0) return <p>Sem temas disponíveis.</p>;

  const next = () => {
    setStartIndex((prev) => (prev + 1) % themes.length);
  };

  const prev = () => {
    setStartIndex((prev) => (prev - 1 + themes.length) % themes.length);
  };

  const produtosVisiveis = Array.from({ length: visibleCount }).map((_, i) => {
    const index = (startIndex + i) % themes.length;
    return themes[index];
  });

  return (
    <div className={styles.store}>
      <header className={styles.header}>
        <Link to="/home">
          <img src={back} alt="Página Anterior" />
          Voltar
        </Link>
        <img src={logo} alt="Logo" className={styles.logo} />
      </header>
      <section>
        <h1>Loja</h1>
        <div className={styles.playerData}>
          <div className={styles.player}>
            <img
              src={images[3]}
              alt="Avatar do usuário"
              width={140}
              height={140}
            />
            <p>{`${player?.name}`}</p>
          </div>
          <div className={styles.playerCoins}>
            <p>
              Saldo: <span>{`${player?.balance}`}</span>
              <BadgeDollarSign color="#febb0b" />
            </p>
            <button className="secondary-btn">Comprar Moedas</button>
          </div>
        </div>
      </section>

      <div className={styles.carouselContainer}>
        <button onClick={prev} className={styles.left} aria-label="Anterior">
          <img src={left} alt="Anterior" />
        </button>

        <div className={styles.shelf}>
          {produtosVisiveis.map((item, index) => (
            <div className={styles.product} key={index}>
              <img src={produto} alt={item.name} />
              <p>{item.name}</p>
              <button className={`${styles.purchaseBtn} secondary-btn`}>
                <BadgeDollarSign color="#febb0b" />
                {`${item.cost} `}
              </button>
            </div>
          ))}
        </div>

        <button onClick={next} className={styles.right} aria-label="Próximo">
          <img src={right} alt="Próximo" />
        </button>
      </div>
    </div>
  );
}

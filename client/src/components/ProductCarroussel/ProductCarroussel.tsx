import React from 'react';
import left from '../../assets/store/left-btn.png';
import right from '../../assets/store/right-btn.png';
import styles from './ProductCarroussel.module.scss';
import Product from '../Product/Product';
import { Theme } from '../../models/Theme';

type ITheme = InstanceType<typeof Theme> & { id: number, purchased: boolean };
interface IProductCarrousselProps {
  props: ITheme[];
  decreaseBalance?: (amount: number) => void;
  addTheme?: (themeId: number) => void;
}

export default function ProductCarroussel({ props, decreaseBalance, addTheme }: IProductCarrousselProps) {
  const produtos = props;
  const visibleCount = 3;
  const [startIndex, setStartIndex] = React.useState(0);

  const next = () => {
    setStartIndex((prev) => (prev + 1) % produtos.length);
  };

  const prev = () => {
    setStartIndex((prev) => (prev - 1 + produtos.length) % produtos.length);
  };

  const produtosVisiveis = Array.from({ length: visibleCount }).map((_, i) => {
    const index = (startIndex + i) % produtos.length;
    return produtos[index];
  });

  return (
    <div className={styles.carouselContainer}>
      <button onClick={prev} className={styles.left} aria-label="Anterior">
        <img src={left} alt="Anterior" />
      </button>

      <div className={styles.shelf}>
        {produtosVisiveis.map((item, index) => (
          <Product key={index} props={item} decreaseBalance={decreaseBalance} addTheme={addTheme} />
        ))}
      </div>

      <button onClick={next} className={styles.right} aria-label="Próximo">
        <img src={right} alt="Próximo" />
      </button>
    </div>
  );
}

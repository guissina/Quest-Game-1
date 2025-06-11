import styles from './Product.module.scss';
import produto from '../../assets/store/produto.png';
import { BadgeDollarSign, X } from 'lucide-react';
import React from 'react';
import thumbnail from '../../assets/store/produto.png';
import { Theme } from '../../models/Theme';


interface IProductProps {
  props: Theme
  decreaseBalace?: (amount: number) => void;
  addTheme?: (themeId: string) => void;
}

export default function Product({ props, decreaseBalace }: IProductProps) {
  const { name, cost } = props;
  const [isModalOpen, setIsModalOpen] = React.useState(false);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  return (
    <>
      <div className={styles.product}>
        <img src={produto} alt={name} />
        <p>{name}</p>
        <button
          className={`${styles.purchaseBtn} secondary-btn`}
          onClick={openModal}
        >
          <BadgeDollarSign color="#febb0b" />
          {cost}
        </button>
      </div>
      {isModalOpen && (
        <div className={styles.modalOverlay} onClick={closeModal}>
          <div
            className={styles.modalContent}
            onClick={(e) => e.stopPropagation()}
          >
            <button className={styles.closeButton} onClick={closeModal}>
              <X size={24} />
            </button>
            <h2>{name}</h2>
            <div className={styles.productPannel}>
              <img src={thumbnail} alt="pack de temas" />
              <ul>
                <li>{name}</li>
              </ul>
            </div>

            <button className={`${styles.comprar} btn`} onClick={() => { decreaseBalace?.(cost) }} >
              <p className={'secondary-btn'}>
                <BadgeDollarSign color="#febb0b" />
                {cost}
              </p>
              Comprar
            </button>
          </div>
        </div >
      )
      }
    </>
  );
}

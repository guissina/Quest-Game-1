import styles from './Product.module.scss';
import produto from '../../assets/store/produto.png';
import { BadgeDollarSign, X } from 'lucide-react';
import React from 'react';
import thumbnail from '../../assets/store/produto.png';
import { Theme } from '../../models/Theme';


type ITheme = InstanceType<typeof Theme> & { purchased: boolean };
interface IProductProps {
  props: ITheme;
  decreaseBalance?: (amount: number) => void;
  addTheme?: (themeId: number) => void;
}

export default function Product({ props, decreaseBalance, addTheme }: IProductProps) {
  const { name, cost, purchased } = props;
  const [isModalOpen, setIsModalOpen] = React.useState(false);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  return (
    <>
      <div className={styles.product}>
        <img src={produto} alt={name} />
        <p>{name}</p>
        <button
          className={`${styles.purchaseBtn} ${purchased ? styles.disabled : ""} secondary-btn`} disabled={purchased}
          onClick={openModal}
        >
          {purchased ?
            'Comprado' :
            <>
              <BadgeDollarSign color="#febb0b" />
              {cost}
            </>
          }
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

            <button className={`${styles.comprar}  btn`} onClick={() => {
              if (purchased) return
              addTheme?.(props.id);
              decreaseBalance?.(cost);
              closeModal();
            }} >
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

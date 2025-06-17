import React from 'react';
import { getAvatarUrl } from '../../utils/avatar';
import { Player } from '../../models/Player';
import { BadgeDollarSign, X } from 'lucide-react';
import styles from './StoreCard.module.scss';


interface IStoreCardProps {
  player: Player;
  addBalance: (amount: number) => void;
}

export default function StoreCard({
  player,
  addBalance,
}: IStoreCardProps) {
  const { name, balance, avatarIndex } = player;
  const [isModalOpen, setIsModalOpen] = React.useState(false);
  const [selectedAmount, setSelectedAmount] = React.useState<number | null>(
    null,
  );

  const avatarUrl = getAvatarUrl(avatarIndex);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  return (
    <>
      <div className={styles.playerData}>
        <div className={styles.player}>
          <img
            src={avatarUrl}
            alt="Avatar do usuário"
            width={140}
            height={140}
          />
          <p>{name}</p>
        </div>
        <div className={styles.playerCoins}>
          <p>
            Saldo: <span>{balance}</span>
            <BadgeDollarSign color="#febb0b" />
          </p>
          <button className="secondary-btn" onClick={openModal}>
            Comprar moedas
          </button>
        </div>
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
            <h2>Pagamento</h2>
            <p>Selecione a quantidade de Moedas:</p>
            <div>
              {[100, 500, 1000, 2000].map((amount) => (
                <button
                  key={amount}
                  className={`secondary-btn ${selectedAmount === amount ? styles.selected : ''
                    }`}
                  onClick={() => setSelectedAmount?.(amount)}
                >
                  <BadgeDollarSign />
                  {amount}
                </button>
              ))}
            </div>
            <button
              className={'btn'}
              onClick={() => {
                if (selectedAmount !== null) {
                  addBalance?.(selectedAmount);
                  closeModal();
                }
              }}
              disabled={selectedAmount === null}
            >
              Comprar
            </button>
          </div>
        </div >
      )
      }
    </>
  );
}

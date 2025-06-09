import styles from './FormWrapper.module.scss';
import logo from './../../assets/QUEST.png';
import type { ReactNode } from 'react';

type Props = {
  children: ReactNode;
};

export default function FormWrapper({ children }: Props) {
  return (
    <div className={styles.wrapper}>
      <img src={logo} alt="Logo" className={styles.logo} />
      <div className={styles.childrenContainer}>{children}</div>
    </div>
  );
}

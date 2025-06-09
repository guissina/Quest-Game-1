import { Link } from 'react-router-dom';
import styles from './Home.module.scss';
import logo from './../../assets/QUEST.png';

export default function Home() {
  return (
    <>
      <div className={styles.home}>
        <img src={logo} alt="Logo" className={styles.logo} />
        <Link to="/login" className={'btn'}>
          Jogar
        </Link>
      </div>
    </>
  );
}

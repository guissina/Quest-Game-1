import { Link, useNavigate } from 'react-router-dom';
import logo from '../../assets/QUEST.png';
import styles from './Header.module.scss';

interface HeaderProps {
    playerName: string;
    onLogout: () => void;
}

export default function Header({ playerName, onLogout }: HeaderProps) {
    const navigate = useNavigate();
    
    const handleLogout = () => {
        onLogout();
        navigate('/');
    };

    return (
        <header className={styles.header}>
            <p className={styles.headerText}>
                <b>Seja Bem-Vindo</b>, {playerName}
            </p>
            <img src={logo} alt="logo" className={styles.logo} />
            <span className={styles.actions}>
                <Link to="/store" className={`${styles.actionStore} secondary-btn`}>
                    Acessar a Loja
                </Link>
                <button 
                    onClick={handleLogout} 
                    className={`${styles.actionLogout} secondary-btn`}
                >
                    Sair
                </button>
            </span>
        </header>
    );
}

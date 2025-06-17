import styles from './ProfileCard.module.scss';
import { Link } from 'react-router-dom';

interface ProfileCardProps {
    avatarUrl: string;
}

export default function ProfileCard({ avatarUrl }: ProfileCardProps) {
    return (
        <div className={styles.profile}>
            <img src={avatarUrl}
                alt="Avatar do usuário"
                className={styles.avatar} />
            <Link to="/edit-profile" className={`${styles.profileLink} secondary-btn`}>
                Meu Perfil
            </Link>
        </div>
    );
}

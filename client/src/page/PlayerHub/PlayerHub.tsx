import { useAuth } from "../../contexts/AuthContext";
import styles from "./PlayerHub.module.scss";

import image1 from "../../assets/avatar/avatar1.png";
import image2 from "../../assets/avatar/avatar2.png";
import image3 from "../../assets/avatar/avatar3.png";
import image4 from "../../assets/avatar/avatar4.png";
import image5 from "../../assets/avatar/avatar5.png";
import image6 from "../../assets/avatar/avatar6.png";
import Header from "../../components/Header/Header";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import SessionForm from "../../components/SessionForm/SessionForm";
import SessionBrowser from "../../components/SessionBrowser/SessionBrowser";

const images = [image1, image2, image3, image4, image5, image6];

export default function PlayerHub() {
    const { user, logout } = useAuth();

    const avatarIndex = (user as any)?.avatar ?? 0;
    const avatarUrl = images[avatarIndex];

    const handleJoinRoomById = (sessionId: string) => {
        console.log("Entrar na sessão:", sessionId);
    };

    const handleCreateRoom = () => {
        console.log("Criar nova sala");
    };

    return (
        <div className={styles.playerHub}>
            <Header playerName={user?.name ?? "Jogador"} onLogout={logout} />

            <section className={styles.section}>
                <ProfileCard avatarUrl={avatarUrl} />

                <SessionForm onJoin={handleJoinRoomById} />

                <SessionBrowser
                    onJoinRoom={handleJoinRoomById}
                    onCreateRoom={handleCreateRoom}
                />
            </section>
        </div>
    );
}

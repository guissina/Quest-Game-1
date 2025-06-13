import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import { useRoomWebSocket } from "../../hooks/useRoomWebSocket";
import Header from "../../components/Header/Header";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import SessionForm from "../../components/SessionForm/SessionForm";
import SessionBrowser from "../../components/SessionBrowser/SessionBrowser";
import styles from "./PlayerHub.module.scss";

import image1 from "../../assets/avatar/avatar1.png";
import image2 from "../../assets/avatar/avatar2.png";
import image3 from "../../assets/avatar/avatar3.png";
import image4 from "../../assets/avatar/avatar4.png";
import image5 from "../../assets/avatar/avatar5.png";
import image6 from "../../assets/avatar/avatar6.png";

const images = [image1, image2, image3, image4, image5, image6];

export default function PlayerHub() {
    const { user, logout } = useAuth();
    const navigate = useNavigate();
    const { ready, sessionId, createRoom, joinRoom } = useRoomWebSocket();

    const avatarIndex = (user as any)?.avatarIndex ?? 0;
    const avatarUrl = images[avatarIndex];

    useEffect(() => {
        if (sessionId)
            navigate(`/session/${sessionId}`);
    }, [sessionId, navigate]);

    if (!ready) {
        return (
            <div className={styles.playerHub}>
                <Header playerName={user?.name ?? ""} onLogout={logout} />
                <p className={styles.loading}>Connecting…</p>
            </div>
        );
    }

    const handleJoinRoomById = (sessionId: string) => {
        if (!user) return;
        joinRoom(sessionId);
    };

    const handleCreateRoom = () => {
        if (!user) return;
        createRoom();
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

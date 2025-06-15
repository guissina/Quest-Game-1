import { useEffect } from "react";
import { useAuth } from "../../contexts/AuthContext";
import { useRoom } from "../../contexts/RoomContext";
import Header from "../../components/Header/Header";
import ProfileCard from "../../components/ProfileCard/ProfileCard";
import SessionForm from "../../components/SessionForm/SessionForm";
import SessionBrowser from "../../components/SessionBrowser/SessionBrowser";
import styles from "./PlayerHub.module.scss";
import { getAvatarUrl } from "../../utils/avatar";

export default function PlayerHub() {
    const { user, logout } = useAuth();
    const {
        ready,
        publicRooms,
        createRoom,
        joinRoom,
        listPublicRooms
    } = useRoom();

    const avatarIndex = (user as any)?.avatarIndex ?? 0;
    const avatarUrl = getAvatarUrl(avatarIndex);

    useEffect(() => {
        if (ready)
            listPublicRooms();
    }, [ready, listPublicRooms]);

    if (!ready) {
        return (
            <div className={styles.playerHub}>
                <Header playerName={user?.name ?? ""} onLogout={logout} />
                <p className={styles.loading}>Connecting…</p>
            </div>
        );
    }

    return (
        <div className={styles.playerHub}>
            <Header playerName={user?.name ?? "Jogador"} onLogout={logout} />

            <section className={styles.section}>
                <ProfileCard avatarUrl={avatarUrl} />

                <SessionForm onJoin={joinRoom} />

                <SessionBrowser
                    publicRooms={publicRooms}
                    onJoinRoom={joinRoom}
                    onCreateRoom={createRoom}
                />
            </section>
        </div>
    );
}

import { BrowserRouter } from "react-router-dom";
import { AuthProvider } from "./contexts/AuthContext";
import AppRoutes from "./routes/AppRoutes";
import { useRef, useEffect, useState } from "react";
import painkiller from "./assets/music/Painkiller.mp3";
import wastedYears from "./assets/music/wasted_years.mp3";
import highway from "./assets/music/Highway.mp3";

export default function App() {
    const audioRef = useRef<HTMLAudioElement | null>(null);
    const [currentTrackIndex, setCurrentTrackIndex] = useState(0);
    const [playlist, setPlaylist] = useState<string[]>([]);

    const soundTracks = [painkiller, wastedYears, highway];

    const shuffleArray = (array: string[]) => {
        const shuffled = [...array];
        for (let i = shuffled.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
        }
        return shuffled;
    };

    useEffect(() => {
        const shuffledPlaylist = shuffleArray(soundTracks);
        setPlaylist(shuffledPlaylist);
    }, []);

    const playNextTrack = () => {
        if (playlist.length === 0) return;

        const nextIndex = (currentTrackIndex + 1) % playlist.length;
        setCurrentTrackIndex(nextIndex);

        if (audioRef.current) {
            audioRef.current.src = playlist[nextIndex];
            audioRef.current.play().catch(console.error);
        }
    };

    useEffect(() => {
        if (playlist.length === 0) return;

        audioRef.current = new Audio(playlist[currentTrackIndex]);
        audioRef.current.volume = 0.2;

        const handleTrackEnd = () => {
            playNextTrack();
        };

        audioRef.current.addEventListener('ended', handleTrackEnd);

        const playAudio = () => {
            audioRef.current?.play().catch(console.error);
            document.removeEventListener('click', playAudio);
            document.removeEventListener('keydown', playAudio);
        };

        document.addEventListener('click', playAudio);
        document.addEventListener('keydown', playAudio);

        return () => {
            if (audioRef.current) {
                audioRef.current.removeEventListener('ended', handleTrackEnd);
                audioRef.current.pause();
                audioRef.current = null;
            }
            document.removeEventListener('click', playAudio);
            document.removeEventListener('keydown', playAudio);
        };
    }, [playlist, currentTrackIndex]);

    return (
        <AuthProvider>
            <BrowserRouter>
                <AppRoutes />
            </BrowserRouter>
        </AuthProvider>
    );
}

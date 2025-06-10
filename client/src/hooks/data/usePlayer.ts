import { useEffect, useState, useCallback } from "react";
import { Player } from "../../models/Player";
import {
    getPlayerById,
    getPlayers,
    addBalance,
    decreaseBalance,
    addTheme,
    loginPlayer,
    registerPlayer
} from "../../services/playerServices";
import { extractErrorMessage } from "../../services/api";

export const usePlayers = () => {
    const [players, setPlayers] = useState<Player[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const fetchPlayers = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const data: Player[] = await getPlayers();
            setPlayers(data);
        } catch (err: any) {
            setError(extractErrorMessage(err));
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchPlayers();
    }, []);

    return {
        players,
        loading,
        error,
        fetchPlayers,
    };
};

export const usePlayer = (playerId: string) => {
    const [player, setPlayer] = useState<Player | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const fetchPlayer = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const data: Player = await getPlayerById(playerId);
            setPlayer(data);
        } catch (err: any) {
            setError(extractErrorMessage(err));
        } finally {
            setLoading(false);
        }
    }, [playerId]);

    useEffect(() => {
        fetchPlayer();
    }, []);

    const addPlayerBalance = useCallback(
        async (balance: number) => {
            setLoading(true);
            setError(null);
            try {
                await addBalance(playerId, balance);
                await fetchPlayer();
            } catch (err: any) {
                setError(extractErrorMessage(err));
                throw err;
            } finally {
                setLoading(false);
            }
        },
        [playerId, fetchPlayer]
    );

    const decreasePlayerBalance = useCallback(
        async (balance: number) => {
            setLoading(true);
            setError(null);
            try {
                await decreaseBalance(playerId, balance);
                await fetchPlayer();
            } catch (err: any) {
                setError(extractErrorMessage(err));
                throw err;
            } finally {
                setLoading(false);
            }
        },
        [playerId, fetchPlayer]
    );

    const addPlayerTheme = useCallback(
        async (themeId: string) => {
            setLoading(true);
            setError(null);
            try {
                await addTheme(playerId, themeId);
                await fetchPlayer();
            } catch (err: any) {
                setError(extractErrorMessage(err));
                throw err;
            } finally {
                setLoading(false);
            }
        },
        [playerId, fetchPlayer]
    );

    return {
        player,
        loading,
        error,
        fetchPlayer,
        addPlayerBalance,
        decreasePlayerBalance,
        addPlayerTheme,
    };
};

// Hook para autenticação (login/register)
export const usePlayerAuth = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const login = useCallback(async (email: string, password: string): Promise<Player> => {
        setLoading(true);
        setError(null);
        try {
            const player = await loginPlayer(email, password);
            return player;
        } catch (err: any) {
            const errorMessage = extractErrorMessage(err);
            setError(errorMessage);
            throw err;
        } finally {
            setLoading(false);
        }
    }, []);

    const register = useCallback(async (name: string, email: string, password: string): Promise<Player> => {
        setLoading(true);
        setError(null);
        try {
            const player = await registerPlayer(name, email, password);
            return player;
        } catch (err: any) {
            const errorMessage = extractErrorMessage(err);
            setError(errorMessage);
            throw err;
        } finally {
            setLoading(false);
        }
    }, []);

    return {
        loading,
        error,
        login,
        register,
    };
};
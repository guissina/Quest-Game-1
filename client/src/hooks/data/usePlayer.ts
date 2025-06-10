import { useEffect, useState, useCallback } from "react";
import { Player } from "../../models/Player";
import { getPlayerById, getPlayers, addBalance, decreaseBalance } from "../../services/playerServices";
import { extractErrorMessage } from "../../services/api";

export const usePlayer = (playerId: string) => {
    const [players, setPlayers] = useState<Player[]>([]);
    const [player, setPlayer] = useState<Player | null>(null);
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

    useEffect(() => {
        fetchPlayers();
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

    return {
        player,
        players,
        loading,
        error,
        fetchPlayer,
        fetchPlayers,
        addPlayerBalance,
        decreasePlayerBalance,
    };
};
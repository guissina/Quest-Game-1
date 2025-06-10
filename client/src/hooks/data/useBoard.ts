import { useEffect, useState, useCallback } from "react";
import { Board } from "../../models/Board";
import { getBoards } from "../../services/boardService";
import { extractErrorMessage } from "../../services/api";

export const useBoard = () => {
    const [boards, setBoards] = useState<Board[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetchBoards();
    }, []);

    const fetchBoards = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const data: Board[] = await getBoards();
            setBoards(data);
        } catch (err: any) {
            setError(extractErrorMessage(err));
        } finally {
            setLoading(false);
        }
    }, []);

    return {
        boards,
        loading,
        error,
        fetchBoards,
    };
};

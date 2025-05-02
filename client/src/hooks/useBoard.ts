import { useEffect, useState, useCallback } from "react";
import { Board } from "../models/Board";
//import { getBoards, getBoardById } from "../services/boardService";
import { getBoards, getBoardById } from "../mocks/boardService.mock"; // TODO Usar a API real
import { extractErrorMessage } from "../services/api";

export const useBoard = () => {
    const [boards, setBoards] = useState<Board[]>([]);
    const [selectedBoard, setSelectedBoard] = useState<Board | null>(null);

    // TODO Error e Loading compartilhados, revisar
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

    const fetchBoardById = useCallback(
        async (id: string) => {
            setLoading(true);
            setError(null);
            try {
                const board = await getBoardById(id);
                setSelectedBoard(board);
            } catch (err: any) {
                setError(extractErrorMessage(err));
            } finally {
                setLoading(false);
            }
        },
        []
    );

    return {
        boards,
        selectedBoard,
        loading,
        error,
        fetchBoards,
        fetchBoardById
    };
};

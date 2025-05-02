import { Board, BoardProps } from "../models/Board";
import { api } from "./api";

export const getBoards = async (): Promise<Board[]> => {
    const raw: BoardProps[] = await api.get("boards").then((res) => res.data);

    return raw.map((props) => new Board(props));
};

export const getBoardById = async (id: string): Promise<Board> => {
    const raw: BoardProps = await api.get(`boards/${id}`).then((res) => res.data);

    return new Board(raw);
};

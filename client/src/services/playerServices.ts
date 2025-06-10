import { Player, PlayerProps } from "../models/Player";
import { api } from "./api";

export const getPlayers = async (): Promise<Player[]> => {
    const raw: PlayerProps[] = await api.get("players").then((res) => res.data);
    return raw.map((props) => new Player(props));
};

export const getPlayerById = async (id: string): Promise<Player> => {
    const raw: PlayerProps = await api.get(`players/${id}`).then((res) => res.data);
    return new Player(raw);
};

export const addBalance = async (id: string, balance: number): Promise<void> => {
    await api.patch(`players/${id}/addBalance`, { balance });
}

export const decreaseBalance = async (id: string, balance: number): Promise<void> => {
    await api.patch(`players/${id}/decreaseBalance`, { balance });
}

export const addTheme = async (id: string, themeId: string): Promise<void> => {
    await api.patch(`players/${id}/addTheme`, { themeId });
}

//TODO: Finish and Implement Login
export const loginPlayer = async (email: string, password: string): Promise<Player> => {
    const raw: PlayerProps = await api.post("players/login", { email, password }).then((res) => res.data);
    return new Player(raw);
}

//TODO: Finish and Implement Register
export const registerPlayer = async (name: string, email: string, password: string): Promise<Player> => {
    const raw: PlayerProps = await api.post("players", { name, email, password }).then((res) => res.data);
    return new Player(raw);
}
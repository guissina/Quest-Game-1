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

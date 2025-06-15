import { Player, PlayerProps } from "../models/Player";
import { api } from "./api";

export const getPlayers = async (): Promise<Player[]> => {
    const raw: PlayerProps[] = await api.get("players").then((res) => res.data);
    return raw.map((props) => new Player(props));
};

export const getPlayerById = async (id: number): Promise<Player> => {
    const raw: PlayerProps = await api.get(`players/${id}`).then((res) => res.data);
    return new Player(raw);
};

export const addBalance = async (id: number, balance: number): Promise<Player> => {
    const raw = await api.patch(`players/${id}/addBalance`, { balance });
    return new Player(raw.data);
}

export const decreaseBalance = async (id: number, balance: number): Promise<Player> => {
    const raw = await api.patch(`players/${id}/decreaseBalance`, { balance });
    return new Player(raw.data);
}

export const addTheme = async (id: number, themeId: number, balance: number): Promise<void> => {
    await api.patch(`players/${id}/addTheme`, { themeId, balance });
}

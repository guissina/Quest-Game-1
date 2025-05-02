import { Player } from "../models/Player";
import { api } from "./api";

interface PlayerDTO {
    id: string;
    name: string;
}

export const getPlayers = async (): Promise<Player[]> => {
    const dto: PlayerDTO[] = await api.get("players").then((res) => res.data);

    return dto.map(dto => new Player(dto.id, dto.name));
};

export const getPlayerById = async (id: string): Promise<Player> => {
    const dto: Player = await api.get(`players/${id}`).then((res) => res.data);

    return new Player(dto.id, dto.name, dto.score, dto.movementTokens);
};

export const createPlayer = async (name: string): Promise<Player> => {
    const dto: Player = await api.post("players", { name }).then((res) => res.data);

    return new Player(dto.id, dto.name, dto.score, dto.movementTokens);
};

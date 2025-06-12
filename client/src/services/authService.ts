import { Player, PlayerProps } from "../models/Player";
import { api } from "./api";

const STORAGE_KEY = "questgame_user";

export const authService = {

    async login(email: string, password: string): Promise<Player> {
        const raw: PlayerProps = await api
            .post("login", { email, password })
            .then(res => res.data);
        const player = new Player(raw);
        this.setCurrentUser(player);
        return player;
    },

    async register(name: string, email: string, password: string): Promise<Player> {
        const raw: PlayerProps = await api
            .post("players", { name, email, password })
            .then(res => res.data);
        const player = new Player(raw);
        this.setCurrentUser(player);
        return player;
    },

    getCurrentUser(): Player | null {
        const json = localStorage.getItem(STORAGE_KEY);
        if (!json) return null;
        const props: PlayerProps = JSON.parse(json);
        return new Player(props);
    },

    setCurrentUser(player: Player) {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(player));
    },

    clearCurrentUser() {
        localStorage.removeItem(STORAGE_KEY);
    }
};

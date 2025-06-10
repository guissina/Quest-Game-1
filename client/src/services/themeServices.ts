import { Theme, ThemeProps } from "../models/Theme";
import { api } from "./api";

export const getThemes = async (): Promise<Theme[]> => {
    const raw: ThemeProps[] = await api.get("themes").then((res) => res.data);
    return raw.map((props) => new Theme(props));
};

export const getThemeById = async (id: string): Promise<Theme> => {
    const raw: ThemeProps = await api.get(`themes/${id}`).then((res) => res.data);
    return new Theme(raw);
}

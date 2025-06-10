import { useEffect, useState, useCallback } from "react";
import { Theme } from "../../models/Theme";
import { getThemes } from "../../services/themeServices";
import { extractErrorMessage } from "../../services/api";

export const useTheme = () => {
    const [themes, setThemes] = useState<Theme[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetchThemes();
    }, []);

    const fetchThemes = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const data: Theme[] = await getThemes();
            setThemes(data);
        } catch (err: any) {
            setError(extractErrorMessage(err));
        } finally {
            setLoading(false);
        }
    }, []);

    return {
        themes,
        loading,
        error,
        fetchThemes,
    };
};

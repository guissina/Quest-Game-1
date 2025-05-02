import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:3000";

export const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        "Content-Type": "application/json",
    },
});

export const extractErrorMessage = (error: unknown): string => {
    if (axios.isAxiosError(error))
        return error.response?.data?.message || error.message;

    return (error as Error).message;
};

import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vite.dev/config/
export default defineConfig({
    plugins: [react()],
    define: {
        global: "window",
    },
    optimizeDeps: {
        include: ["sockjs-client"],
    },
    build: {
        outDir: 'dist',
        sourcemap: false, // Reduz tamanho do build
    },
    // Para garantir que assets funcionem em qualquer caminho
    base: './',
});
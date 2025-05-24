import { useState, useEffect } from "react";
import { PlayerProps } from "../models/Player";

interface LobbyPageProps {
    sessionId: string | null;
    players: PlayerProps[];
    started: boolean;
    createRoom: (playerId: number) => void;
    joinRoom: (sessionId: string, playerId: number) => void;
    startRoom: (boardId: number, initialTokens: number) => void;
}

export default function LobbyPage({ sessionId, players, started, createRoom, joinRoom, startRoom }: LobbyPageProps) {
    
    const [inputSessionId, setInputSessionId] = useState("");
    const [inputPlayerId, setInputPlayerId] = useState<number | "">("");
    const [inputBoardId, setInputBoardId] = useState<number | "">("");
    const [inputInitialTokens, setInputInitialTokens] = useState<number | "">("");

    useEffect(() => {
        if (sessionId) setInputSessionId(sessionId);
    }, [sessionId]);

    useEffect(() => {
        if (inputPlayerId !== "") localStorage.setItem("userId", inputPlayerId.toString());
    }, [inputPlayerId]);

    const handleCreate = () => {
        if (!inputPlayerId) return;
        createRoom(Number(inputPlayerId));
    };

    const handleJoin = () => {
        if (!inputSessionId || !inputPlayerId) return;
        joinRoom(inputSessionId, Number(inputPlayerId));
    };

    const handleStart = () => {
        if (!sessionId || !inputBoardId || !inputInitialTokens) return;
        startRoom(Number(inputBoardId), Number(inputInitialTokens));
    };

    return (
        <div style={{ padding: 20, maxWidth: 400, margin: "auto" }}>
            <h2>Game Lobby</h2>

            {/* Botão de criação só aparece antes de termos sessionId */}
            {!sessionId && (
                <button
                    onClick={handleCreate}
                    style={{ marginBottom: 16, width: "100%" }}
                >
                    Create Session
                </button>
            )}

            {/* Parte de join sempre visível */}
            <div style={{ marginBottom: 16 }}>
                <input
                    type='text'
                    placeholder='Session ID'
                    value={inputSessionId}
                    onChange={(e) => setInputSessionId(e.target.value)}
                    style={{ width: "100%", marginBottom: 8 }}
                />
                <input
                    type='number'
                    placeholder='Your Player ID'
                    value={inputPlayerId}
                    onChange={(e) =>
                        setInputPlayerId(
                            e.target.value === "" ? "" : Number(e.target.value)
                        )
                    }
                    style={{ width: "100%", marginBottom: 8 }}
                />
                <button
                    onClick={handleJoin}
                    disabled={!inputSessionId || !inputPlayerId}
                    style={{ width: "100%" }}
                >
                    Join Session
                </button>
            </div>

            {/* Configuração do jogo só depois que a sala existe */}
            {sessionId && (
                <div style={{ marginBottom: 16 }}>
                    <input
                        type='number'
                        placeholder='Board ID'
                        value={inputBoardId}
                        onChange={(e) =>
                            setInputBoardId(
                                e.target.value === ""
                                    ? ""
                                    : Number(e.target.value)
                            )
                        }
                        style={{ width: "100%", marginBottom: 8 }}
                    />
                    <input
                        type='number'
                        placeholder='Initial Tokens'
                        value={inputInitialTokens}
                        onChange={(e) =>
                            setInputInitialTokens(
                                e.target.value === ""
                                    ? ""
                                    : Number(e.target.value)
                            )
                        }
                        style={{ width: "100%", marginBottom: 8 }}
                    />
                    <button
                        onClick={handleStart}
                        disabled={
                            started || !inputBoardId || !inputInitialTokens
                        }
                        style={{ width: "100%" }}
                    >
                        {started ? "Game Started" : "Start Game"}
                    </button>
                </div>
            )}

            {/* Informações da sala: sessionId e lista de players */}
            {sessionId && (
                <>
                    <p>
                        <b>Session ID:</b> <code>{sessionId}</code>
                    </p>
                    <h3>Players ({players.length})</h3>
                    {players.length === 0 ? (
                        <p>
                            <i>No players yet</i>
                        </p>
                    ) : (
                        <ul>
                            {players.map((p) => (
                                <li key={p.id}>
                                    {p.id}: {p.name}
                                </li>
                            ))}
                        </ul>
                    )}
                </>
            )}
        </div>
    );
}

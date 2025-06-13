import { useState } from "react";
import { PlayerProps } from "../models/Player";

interface LobbyPageProps {
    sessionId: string;
    myPlayerId: number;
    players: PlayerProps[];
    started: boolean;
    startRoom: (boardId: number, initialTokens: number, themeIds: number[]) => void;
}

export default function LobbyPage({ sessionId, myPlayerId, players, started, startRoom }: LobbyPageProps) {
    const [boardId, setBoardId] = useState<number | "">("");
    const [initialTokens, setInitialTokens] = useState<number | "">("");

    // TODO Review... assume the first player in `players` array is the creator
    const isCreator = players[0]?.id === myPlayerId;

    const handleStart = () => {
        if (!boardId || !initialTokens) return;
        // TODO: replace the hard-coded theme list with your actual UI for theme selection
        const themeIds = [1, 2, 3, 4, 5, 6];
        startRoom(Number(boardId), Number(initialTokens), themeIds);
    };
 
    return (
      <div
        style={{
          padding: "24px",
          maxWidth: "480px",
          margin: "40px auto",
          backgroundColor: "#f9f9f9",
          borderRadius: "8px",
          boxShadow: "0 0 12px rgba(0, 0, 0, 0.1)",
          fontFamily: "sans-serif",
        }}
      >
        <h2 style={{ marginBottom: "12px" }}>Lobby</h2>
        <p style={{ fontSize: "14px", marginBottom: "24px", color: "#444" }}>
          <strong>Session ID:</strong> <code>{sessionId}</code>
        </p>

        <h3 style={{ marginBottom: "8px" }}>Players ({players.length})</h3>
        {players.length === 0 ? (
          <p style={{ fontStyle: "italic", color: "#777" }}>No players yet…</p>
        ) : (
          <ul
            style={{ listStyle: "none", paddingLeft: 0, marginBottom: "24px" }}
          >
            {players.map((p) => (
              <li
                key={p.id}
                style={{
                  padding: "6px 0",
                  borderBottom: "1px solid #e0e0e0",
                  color: p.id === myPlayerId ? "#2b7a78" : "#333",
                }}
              >
                {p.name} {p.id === myPlayerId && "(You)"}{" "}
                {p.id === players[0]?.id && "🛡️"}
              </li>
            ))}
          </ul>
        )}

        {isCreator && (
          <div>
            <h4 style={{ marginBottom: "12px" }}>Game Settings</h4>
            <input
              type="number"
              placeholder="Board ID"
              value={boardId}
              onChange={(e) =>
                setBoardId(e.target.value === "" ? "" : Number(e.target.value))
              }
              style={{
                width: "100%",
                padding: "10px",
                marginBottom: "12px",
                border: "1px solid #ccc",
                borderRadius: "4px",
              }}
            />
            <input
              type="number"
              placeholder="Initial Tokens"
              value={initialTokens}
              onChange={(e) =>
                setInitialTokens(
                  e.target.value === "" ? "" : Number(e.target.value)
                )
              }
              style={{
                width: "100%",
                padding: "10px",
                marginBottom: "12px",
                border: "1px solid #ccc",
                borderRadius: "4px",
              }}
            />
            <button
              onClick={handleStart}
              disabled={started || !boardId || !initialTokens}
              style={{
                width: "100%",
                padding: "12px",
                backgroundColor: started ? "#ccc" : "#3f51b5",
                color: "#fff",
                fontWeight: "bold",
                border: "none",
                borderRadius: "4px",
                cursor: started ? "default" : "pointer",
              }}
            >
              {started ? "Game Started" : "Start Game"}
            </button>
          </div>
        )}

        {!isCreator && !started && (
          <p style={{ marginTop: "24px", fontStyle: "italic", color: "#666" }}>
            Waiting for the creator to start the game…
          </p>
        )}
      </div>
    );
}

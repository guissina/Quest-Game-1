import { useState } from "react";
import { useWebSocket } from "../hooks/useWebSocket";
import {Link} from "react-router-dom";

export function HelloPage() {
    const [greeting, setGreeting] = useState("");
    const { sendHello } = useWebSocket(setGreeting);

    return (
        <div>
            <button onClick={() => sendHello("Alice")}>Say Hello</button>
            <p>Server says: {greeting}</p>
            <Link to={"/login"}>
              Login Quest Jogo
            </Link>
        </div>
    );
}

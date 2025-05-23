import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import GamePage from "./page/GamePage";
import { HelloPage } from "./page/HelloPage";
import Login from "./page/login";
import "./App.css";

function App() {
    return (
        <Router>
            <div className='App'>
                <Routes>
                    <Route path="/" element={<HelloPage />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/game" element={<GamePage />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;

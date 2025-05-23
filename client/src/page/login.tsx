//import { useWebSocket } from "../hooks/useWebSocket";

// Login.tsx
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom'; // Para navegação e redirecionamento
import logoQuest from '../assets/images/logo-quest.png';

// Importe seu CSS aqui se não for global
import './style.css'; // Ou o caminho correto para style.css

// Importe as funções de autenticação (a serem criadas/adaptadas)
// import { loginUser } from './auth'; // Exemplo

// Props (se houver alguma necessária no futuro)
interface LoginProps {}

const Login: React.FC<LoginProps> = () => {
  const [emailOrUsername, setEmailOrUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleLoginSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setErrorMessage(''); // Limpa mensagens de erro anteriores

    if (!emailOrUsername || !password) {
      setErrorMessage('Por favor, preencha todos os campos.');
      return;
    }

    // --- Simulação da Lógica de Login ---
    // Substitua esta parte pela chamada à sua função real de login (auth.js)
    // const result = await loginUser(emailOrUsername, password);
    // Exemplo de simulação:
    const MOCK_USER_EMAIL = 'fulaninho@gmail.com';
    const MOCK_USER_PASS = 'password123';

    if ((emailOrUsername === MOCK_USER_EMAIL || emailOrUsername === 'fulaninho') && password === MOCK_USER_PASS) {
      console.log('Login bem-sucedido!');
      // Em uma aplicação real, você armazenaria o token/sessão do usuário aqui
      // localStorage.setItem('loggedInUser', JSON.stringify({ email: MOCK_USER_EMAIL, username: 'fulaninho' }));
      navigate('/meu-perfil'); // Redireciona para o perfil após login bem-sucedido
    } else {
      setErrorMessage('Usuário ou senha inválido.');
    }
    // --- Fim da Simulação ---
  };

  return (
    <div className="body-override-login"> {/* Classe para aplicar fundo específico se necessário */}
      <div className="logo-container">
        <img src={logoQuest} alt="Logo QUEST" />
      </div>

      <div className="container">
        <h1>Bem-vindo</h1>
        <form id="login-form" onSubmit={handleLoginSubmit}>
          <div className="input-group">
            {/* Para o ícone, você pode usar um SVG inline ou uma biblioteca de ícones.
                Exemplo com placeholder de texto para o ícone:
                <span style={{ position: 'absolute', left: '15px', top: '38px', color: '#999', pointerEvents: 'none' }}>@</span>
            */}
            <input
              type="text"
              id="email-username"
              name="email-username"
              placeholder="fulaninho@gmail.com"
              className="has-icon" // Adicione a classe se o CSS a utiliza para padding
              value={emailOrUsername}
              onChange={(e) => setEmailOrUsername(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            {/* <span style={{ position: 'absolute', left: '15px', top: '38px', color: '#999', pointerEvents: 'none' }}>🔒</span> */}
            <input
              type="password"
              id="password"
              name="password"
              placeholder="************"
              className="has-icon"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          {errorMessage && (
            <div id="login-error-message" className="message error-message" style={{ display: 'block' }}>
              {errorMessage}
            </div>
          )}

          <button type="submit" className="btn btn-primary">
            Entrar
          </button>

          <div className="form-links">
            <p>
              <Link to="/cadastro" className="btn-link">
                Não tem conta? Criar conta
              </Link>
            </p>
            <p>
              <Link to="/recuperar-senha" className="btn-link">
                Redefinir minha senha
              </Link>
            </p>
          </div>
        </form>
      </div>
      {/* Adicione o GlobalStyle aqui ou no App.tsx se estiver usando styled-components para o body */}
      
    </div>
  );
};

export default Login;

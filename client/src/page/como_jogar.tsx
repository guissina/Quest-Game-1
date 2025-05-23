// ComoJogar.tsx
import React from 'react';
import { Link } from 'react-router-dom'; // Para navegação

// Importe seu CSS aqui se não for global
// import './style.css'; // Ou o caminho correto para style.css

// Props (se houver alguma necessária no futuro)
interface ComoJogarProps {}

const ComoJogar: React.FC<ComoJogarProps> = () => {
  return (
    <div className="body-override-como-jogar"> {/* Classe para aplicar fundo específico se necessário */}
      <div className="back-button-container">
        {/* Ajustar o 'to' para a página anterior relevante (ex: tela principal do jogo ou login) */}
        <Link to="/login" className="btn-link">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" style={{ width: '1em', height: '1em', marginRight: '5px', verticalAlign: 'middle' }}>
            <path fillRule="evenodd" d="M17 10a.75.75 0 01-.75.75H5.56l2.72 2.72a.75.75 0 11-1.06 1.06l-4-4a.75.75 0 010-1.06l4-4a.75.75 0 011.06 1.06L5.56 9.25H16.25A.75.75 0 0117 10z" clipRule="evenodd" />
          </svg>
          Voltar
        </Link>
      </div>

      <div className="how-to-play-container" style={{ marginTop: '70px' }}>
        <div className="logo-container" style={{ marginBottom: '20px', marginTop: '-10px' }}>
          <img src="/assets/images/logo-quest.png" alt="Logo QUEST" style={{ maxWidth: '180px' }} />
        </div>
        <h2 className="page-title">Como Jogar?</h2>
        <p className="intro-text">
          Bem-vindo ao QUEST! Prepare-se para testar seus conhecimentos em uma jornada cheia de perguntas, desafios e descobertas.
        </p>

        <h3>Escolha um Tema ou Pack de Temas:</h3>
        <ul>
          <li>Os temas variam entre História, Ciência, Música, Entretenimento, Curiosidades e muito mais!</li>
        </ul>

        <h3>Responda às Perguntas:</h3>
        <ul>
          <li>Para responder a uma pergunta, faça uma aposta com as fichas que tem disponível.</li>
          <li>Cada rodada traz uma pergunta com 4 alternativas.</li>
          <li>Você tem tempo limitado para responder sua pergunta.</li>
          <li>Se acertar a pergunta, movimente-se por entre as casas de acordo com sua aposta.</li>
          <li>Se errar a pergunta, permaneça onde está e aguarde sua próxima rodada.</li>
          <li>Após apostar todas as suas fichas, elas voltarão pra você para que o jogo continue.</li>
          <li>Nas casas com dois temas, escolha qual tema você gostaria de responder.</li>
          <li>Vence o jogador que chegar a última casa!</li>
        </ul>
      </div>
      {/* Adicione o GlobalStyle aqui ou no App.tsx se estiver usando styled-components para o body */}
      
    </div>
  );
};

export default ComoJogar;

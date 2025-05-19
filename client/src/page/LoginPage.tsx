import GenericForm from "../components/Form"
import "./LoginPage.scss";
import { useNavigate } from "react-router-dom";

const LoginPage = () => {
  const navigate = useNavigate()
  const fields = [
    {
      name: "username",
      type: "text",
      placeholder: "Email",
      required: true,
    },
    {
      name: "password",
      type: "password",
      placeholder: "Senha",
      required: true,
    },
  ];

  return (
    <div className="login-page-container">
      <img src="/login-page/logo-novo-1.svg" alt="Logo da página de login" />
      <div className="login-page-content">
        <h1>Bem-vindo</h1>
        <GenericForm
          method="POST"
          fields={fields}
          submitLabel="Login"
          variant="login"
          className=""
        />
        <div className="first-access-container">
          <div className="first-access-content" onClick={() => navigate('/register')}>
            Primeiro acesso? Clique aqui e cadastre-se
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;

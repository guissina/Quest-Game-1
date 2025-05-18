import GenericForm from "../components/Form"
import "./LoginPage.scss";

const LoginPage = () => {
  const fields = [
    {
      name: "username",
      type: "text",
      placeholder: "email",
      required: true,
    },
    {
      name: "password",
      type: "password",
      placeholder: "senha",
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
          <div className="first-access-content">
            Primeiro acesso? Clique aqui e cadastre-se
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;

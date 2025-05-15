import GenericForm from "../components/Form"
import "./LoginPage.scss";

const LoginPage = () => {
  const fields = [
    {
      name: "username",
      type: "text",
      placeholder: "Digite o nome do seu usuário",
      required: true,
    },
    {
      name: "password",
      type: "password",
      placeholder: "Digite a sua senha",
      required: true,
    },
  ];

  return (
    <div className="login-page-container">
      <div className="login-page-content">
        <h1>Quest</h1>
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

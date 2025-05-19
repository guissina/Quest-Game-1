import GenericForm from '../components/Form';
import { useNavigate } from 'react-router-dom';
import "./RegisterPage.scss";


const RegisterPage = () => {

    const navigate = useNavigate();
    const fields = [
        {
            name: "username",
            type: "text",
            placeholder: "Nome do usuário",
            required: true,
        },
        {
            name: "email",
            type: "email",
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
        <div className="register-page-container">
            <img src="/login-page/logo-novo-1.svg" alt="Logo da página de cadastro" />
            <div className="register-page-content">
                <h1>Cadastre-se</h1>
                <GenericForm
                    method="POST"
                    fields={fields}
                    submitLabel="Cadastre-se"
                    variant="login"
                    className=""
                />
                <div className="first-access-container">
                    <div className="first-access-content" onClick={() => navigate('/')} >
                        Já tem cadastro? Voltar para tela de login
                    </div>
                </div>
            </div>
        </div>
    );
};


export default RegisterPage
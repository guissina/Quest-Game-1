import "./LoginPage.scss"


const LoginPage = () => {
  return (
    <div className='login-page-container'>
      <div className='login-page-content'>
        <h1>Quest</h1>
        <form className='form'>
          <div className='form-group'>
            <input type="text" id='username' name='username' placeholder='Digite o nome do seu usuário' required/>
          </div>
          <div className='form-group'>
            <input type="password" id='password' name='password' placeholder='Digite a sua senha' required/>
          </div>
          <div className='submit-button-container'>
            <button className='submit-button' type='submit'>Login</button>
          </div>
        </form>
        <div className='first-access-container'>
          <div className='first-access-content'>Primeiro acesso? Clique aqui e cadastre-se</div>
        </div>
      </div>
    </div>
  )
}

export default LoginPage
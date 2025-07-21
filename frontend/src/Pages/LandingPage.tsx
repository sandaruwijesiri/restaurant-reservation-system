import React, { useEffect } from 'react';
import './LandingPage.css';
import { useNavigate } from 'react-router-dom';
import { keycloakLogin, keycloakRegistration } from '../Services/KeyCloakService';

const LandingPage: React.FC = () => {

  const login = () =>{
    // navigate('/login');
    keycloakLogin();
  }

  const register = () =>{
    // navigate('/registration');
    keycloakRegistration();
  }

  return (
    <div className="container">
       <div className="bg bg1" />
       <div className="bg bg2" />
       <div className="bg bg3" />
       <div className='content'>
         <div className="welcome-message">
           <p>Welcome to Memorias!</p>
           <p style={{fontSize: '0.4em'}}>Where memories are made...</p>
         </div>
         <div className="buttons">
             <button onClick={() => login()}>Log in</button>
             <button onClick={() => register()}>Register</button>
         </div>
       </div>
    </div>
  );
}

export default LandingPage;

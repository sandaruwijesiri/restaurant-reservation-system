import React from 'react';
import './LandingPage.css';
import { useNavigate } from 'react-router-dom';

import interiorImg from '../assets/images/interior.jpg';
import food1Img    from '../assets/images/food1.jpg';
import food2Img    from '../assets/images/food2.jpg';

const LandingPage: React.FC = () => {
  const navigate = useNavigate();
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
             <button onClick={() => navigate('/login')}>Log in</button>
             <button onClick={() => navigate('/registration')}>Register</button>
         </div>
       </div>
    </div>
  );
}

export default LandingPage;

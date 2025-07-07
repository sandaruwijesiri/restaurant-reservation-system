import React from 'react';
import './LandingPage.css';
import { useLocation, useNavigate } from 'react-router-dom';

import interiorImg from '../assets/images/interior.jpg';
import food1Img    from '../assets/images/food1.jpg';
import food2Img    from '../assets/images/food2.jpg';

const MessagePage: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { message, isError } = location.state || {};
  return (
    <div className="container">
       <div className="bg bg1" />
       <div className="bg bg2" />
       <div className="bg bg3" />
       <div className='content'>
         <div className="welcome-message">
           <p>{message}</p>
         </div>
         <div className="buttons">
             <button onClick={() => navigate(-1)}>OK</button>
         </div>
       </div>
    </div>
  );
}

export default MessagePage;

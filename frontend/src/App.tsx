import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

import Registration from './Pages/Registration';
import Login from './Pages/Login';
import Home from './Pages/Home';
import LandingPage from './Pages/LandingPage';
import SuccessPage from './Pages/SuccessPage';
import MessagePage from './Pages/MessagePage';
import RedirectionPage from './Pages/RedirectionPage';
import AdminPage from './Pages/AdminPage';

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/registration" element={<Registration />} />
        <Route path="/login" element={<Login />} />
        <Route path="/home" element={<Home />} />
        <Route path="/success" element={<SuccessPage />} />
        <Route path="/message" element={<MessagePage />} />
        <Route path="/redirect" element={<RedirectionPage />} />
        <Route path="/adminPage" element={<AdminPage />} />
      </Routes>
    </Router>
  );
};
export default App;

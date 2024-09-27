import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './styles/common.css';
import './styles/darkribbon.css';
import Dashboard from './pages/Dashboard';
import DashboardAdmin from './pages/DashboardAdmin';
import LogIn from './pages/LogIn';
import SignUp from './pages/SignUp';
import Error from './pages/Error';

function App() {

  const [token, setToken] = useState('use state apptoken');

  return (
    <div className="App">
      <Router>
        <Routes>
        <Route path="/" element={<LogIn apptoken={setToken}/>} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/dashboard" element={<Dashboard apptoken={token}/>} />
        <Route path="/dashboard/admin" element={<DashboardAdmin apptoken={token}/>} />
        <Route path="/processerror" element={<Error />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;

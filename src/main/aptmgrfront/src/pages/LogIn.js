
import React, { useState } from 'react';
import axios from 'axios';
import '../components/Border.css';
import '../components/menu/buttons-small.css';
import { useNavigate } from 'react-router-dom';

const LogIn = props => {
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const history = useNavigate();
    const handleLogin = async () => {
        try {
            if (!name || !password) {
                setError('Please enter both username and password.');
                return;
            }
            const response = await axios.post('/auth/signin', { name, password });
            console.log('Login successful:', response.data);
            props.apptoken(response.data.token);
            if (response.data.roles.some( (role) => role === 'ADMIN')) {
                history('/dashboard/admin');
            } else {
                history('/dashboard');
            }
        } catch (error) {
            console.error('Login failed:', error.response ? error.response.data : error.message);
            setError('Invalid username or password.');
        }
    };

    return (
        <div className='bord-container'>
            <div className='bord'>
                    <div className="divborder">
                        <h2>Please login</h2>
                    </div>
                    <div className="divborder">
                        <input placeholder='Name' id='email' value={name} type='name' onChange={(e) => setName(e.target.value)} />
                    </div>
                    <div className="divborder">
                        <input placeholder='Password' id='password' type='password' value={password} onChange={(e) => setPassword(e.target.value)} />
                    </div>
                    <div className="divborder">
                        {error && <p className="text-danger">{error}</p>}
                    </div>
                    <div className="divborder">
                        <button class="modern-small embossed-link-small" onClick={handleLogin}>Sign in</button>
                    </div>
                    <div className="divborder">
                        <p>Not a member? <a href="/signup" >Register</a></p>
                    </div>
            </div>
        </div>
    );
}

export default LogIn;
import React, { useState } from 'react';
import axios from 'axios';
import '../components/Border.css';
import '../components/menu/buttons-small.css';
import { useNavigate } from 'react-router-dom';

const SignUp = props => {
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');
    const history = useNavigate();
    const handleSignup = async () => {
        try {
            if (!name || !password || !confirmPassword) {
                setError('Please fill in all fields.');
                return;
            }

            if (password !== confirmPassword) {
                throw new Error("Passwords do not match");
            }

            const response = await axios.post('/auth/signup', {
                name,
                password,
            });
            console.log(response.data);
            history('/');
        } catch (error) {
            console.error('Signup failed:', error.response ? error.response.data : error.message);
            setError(error.response ? error.response.data : error.message);
            history('/processerror');
        }
    };

    return (
        <div>
            <div className='bord-container'>
                <div className='bord'>
                        <div className="divborder">
                            <h2>Sign Up Page</h2>
                        </div>
                        <div className="divborder">
                            {error && <p className="text-danger">{error}</p>}
                        </div>
                        <div className="divborder">
                            <input className='depth' id='name' placeholder={"Name"} value={name} type='text' onChange={(e) => setName(e.target.value)} />
                        </div>
                        <div className="divborder">
                            <input className='depth' placeholder='Password' id='password' type='password' value={password} onChange={(e) => setPassword(e.target.value)} />
                        </div>
                        <div className="divborder">
                            <input className='depth' placeholder='Confirm Password' id='confirmPassword' type='password' value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} />
                        </div>
                        <div className="divborder">
                            <button class="modern-small embossed-link-small" onClick={handleSignup}>Sign Up</button>
                        </div>
                        <div className="divborder">
                            <p>Already Register? <a href="/">Login</a></p>
                        </div>
                </div>
            </div>
        </div>
    );
}

export default SignUp;
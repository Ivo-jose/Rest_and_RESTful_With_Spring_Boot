import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
//CSS
import './styles.css'
//Images
import logo from '../../assets/logo.svg';
import padLock from '../../assets/padlock.png';
//Service
import api from '../../services/api';

export default function Login() {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    async function login(e) {
        e.preventDefault();
        const data = {
            username,
            password
        }
        
        try {
            const response = await api.post(`/auth/signin`,data);
            localStorage.setItem('username',username);
            console.log(response)
            localStorage.setItem('accessToken', response.data.accessToken);
            navigate('/books')
        } catch (error) {   
            alert('Login failed! Try agains!');
        }
    }

    return(
        <div className='login-container'>
            <section className="form">
               <img src={logo} alt="Logo" /> 
               <form onSubmit={login}>
                  <h1>Access your account</h1>
                  <input 
                     type="text" 
                     placeholder='Username' 
                     required
                     value={username}
                     onChange={e => setUsername(e.target.value)}
                  />  
                  <input 
                     type="password" 
                     placeholder='Password' 
                     required 
                     value={password}
                     onChange={e => setPassword(e.target.value)}
                  />  
                  <button className='button' type='submit'>Login</button>
               </form>
            </section>
            <img src={padLock} alt="padlock"/>
        </div>
    );
}
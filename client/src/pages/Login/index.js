import React from 'react';
//CSS
import './styles.css'
//Images
import logo from '../../assets/logo.svg';
import padLock from '../../assets/padlock.png';

export default function Login() {
    return(
        <div className='login-container'>
            <section className="form">
               <img src={logo} alt="Logo" /> 
               <form action="">
                  <h1>Access your account</h1>
                  <input type="text" placeholder='Username'/>  
                  <input type="text" placeholder='Password'/>  
                  <button className='button' type='submit'>Login</button>
               </form>
            </section>
            <img src={padLock} alt="padlock"/>
        </div>
    );
}
//Libs
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

//Components
import Login from './pages/Login/index';

export default function AppRoutes() {
    return(
         <BrowserRouter>
            <Routes>
                <Route path="/" element={ <Login/> } />
            </Routes>
         </BrowserRouter>    
    );
}

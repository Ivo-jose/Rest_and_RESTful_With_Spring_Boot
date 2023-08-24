//Libs
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

//Components
import Login from './pages/Login/index';
import Book from './pages/Book';

export default function AppRoutes() {
    return(
         <BrowserRouter>
            <Routes>
                <Route path="/" element={ <Login/> } />
                <Route path="/book"  element={ <Book/> } />
            </Routes>
         </BrowserRouter>    
    );
}

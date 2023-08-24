//Libs
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

//Components
import Login from './pages/Login/index';
import Books from './pages/Books/index';
import NewBook from './pages/NewBook/index';

export default function AppRoutes() {
    return(
         <BrowserRouter>
            <Routes>
                <Route path="/" exact element={ <Login/> } />
                <Route path="/books"  element={ <Books/> } />
                <Route path="/book/new"  element={ <NewBook/> } />
            </Routes>
         </BrowserRouter>    
    );
}

//Lib
import React from "react";
import { Link } from "react-router-dom";
import { FiArrowLeft } from 'react-icons/fi'
//CSS
import './styles.css'
//Images
import logo from '../../assets/logo.svg';


export default function NewBook(){
    return(
        <div className="new-book-container">
            <div className="content">
                <section className="form">
                    <img src={logo} alt="Logo" />
                    <h1>add New Book</h1>
                    <p>Enter the book information and click on 'Add'!</p>
                    <Link className="back-link" to="/books">
                       <FiArrowLeft size={16} color="#251FC5"></FiArrowLeft> 
                       Home
                    </Link>
                </section>
                <form action="">
                    <input type="text" placeholder="Title" />
                    <input type="text" placeholder="Author" />
                    <input type="date" />
                    <input type="text" placeholder="Price" />
                    <button type="submit" className="button">Add</button>
                </form>
            </div>
        </div>
    );
}


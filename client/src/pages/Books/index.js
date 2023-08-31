//Libs
import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FiPower, FiEdit, FiTrash2 } from 'react-icons/fi'
//CSS
import './styles.css';
//Images
import logo from '../../assets/logo.svg';
// Api service
import api from '../../services/api';

export default function Books() {

    const [books, setBooks] = useState([]);
    const [currentPage, setCurrentPage] = useState(0); 
    const [itemsPerPage, setItemsPerPage] = useState(10); 

    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');
    let  [totalPages, setTotalPages] = useState(0);

    const navigate = useNavigate();

    useEffect(() => {
        api.get(`/api/book/v1?page=${currentPage}&size=${itemsPerPage}`, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }    
        }).then(response => {
            setBooks(response.data._embedded.bookVOList);
            setTotalPages(response.data.page.totalPages);
        })
    },[accessToken, currentPage, itemsPerPage])

    return(
        <div className="book-container">
            <header>
                <img src={logo} alt="Logo" />
                <span>Welcome, <strong>{username.substring(0,1).toUpperCase() + username.substring(1)}</strong>!</span>
                <Link className="button" to="/book/new">Add New Book</Link>
                <button type="button">
                    <FiPower size={20} color="#251FE5"></FiPower>
                </button>
            </header>

            <h1>Registered Books</h1>    
            <ul>
               {books.map(book => (
                 <li key={book.id}>
                    <strong>Title:</strong>
                    <p>{book.title}</p>
                    <strong>Author:</strong>
                    <p>{book.author}</p>
                    <strong>Price:</strong>
                    <p>{Intl.NumberFormat('pt-br', {style: 'currency', currency: 'BRL'}).format(book.price)}</p>
                    <strong>Release Date:</strong>
                    <p>{Intl.DateTimeFormat('pt-br').format(new Date(book.launchDate))}</p>

                    <button type="button">
                        <FiEdit size={20} color="#251FC5"></FiEdit>
                    </button>
                    <button type="button">
                        <FiTrash2 size={20} color="#251FC5"></FiTrash2>
                    </button>
                 </li>
               ))} 
            </ul>

            <div id="container-buttons-page">
                <button id="previous" onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 0}>
                    Previous
                </button>
                <p>
                    Page {currentPage + 1} of {totalPages}
                </p>
                <button id="next" onClick={() => setCurrentPage(currentPage + 1)}>
                    Next
                </button>
            </div>
        </div>
    );
}

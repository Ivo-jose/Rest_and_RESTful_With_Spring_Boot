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
    let [inputPage, setInputPage] = useState("");

    const navigate = useNavigate();

    function handleGoToPage(page) {
        if (page >= 0 && page < totalPages) {
            setCurrentPage(page);
        }
    }

    async function logout(){
        localStorage.clear();
        navigate('/')
    }

    async function deleteBook(id) {
        console.log(id)
        try {
            await api.delete(`/api/book/v1/${id}`,{
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })

            setBooks(books.filter(book => book.id !== id))
        } catch (error) {
            alert("Delete faield! Try again!")
        }
    }

    useEffect(() => {
        api.get(`/api/book/v1`, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            },
            params: {
                page: currentPage,
                size: itemsPerPage,
                direction: 'asc'
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
                <button onClick={logout} type="button">
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
                    <button onClick={() => deleteBook(book.id)} type="button">
                        <FiTrash2 size={20} color="#251FC5"></FiTrash2>
                    </button>
                 </li>
               ))} 
            </ul>

            <div id="container-buttons-page">
                <button  onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 0} id="previous"> 
                    Previous
                </button>
                <p>
                    Page {currentPage + 1} of {totalPages}
                </p>
                <button id="next" onClick={() => setCurrentPage(currentPage + 1)} disabled={currentPage === 101}>
                    Next
                </button>
                <div id="specific-page">
                    <input
                        type="number"
                        placeholder={`Page (1 - ${totalPages})`}
                        value={inputPage}
                        onChange={(e) => setInputPage(e.target.value)}
                    />
                    <button onClick={() => handleGoToPage(inputPage - 1)} id="go">
                        Go
                    </button>
                </div>
            </div>
        </div>
    );
}

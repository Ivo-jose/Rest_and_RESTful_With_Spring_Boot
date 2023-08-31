//Lib
import React, {useState, useEffect } from "react";
import { useNavigate, Link, useParams } from "react-router-dom";
import { FiArrowLeft } from 'react-icons/fi'
//CSS
import './styles.css'
//Images
import logo from '../../assets/logo.svg';
//API SERVICE
import api from '../../services/api';

export default function EditBook(){
    
    const [id, setId] = useState('');
    const [author, setAuthor] = useState('');
    const [launchDate, setLaunchDate] = useState('');
    const [price, setPrice] = useState('');
    const [title, setTitle] = useState('');
    const {bookId} = useParams();

    // eslint-disable-next-line no-unused-vars
    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    async function loadBook() {
        try {
            const response = await api.get(`/api/book/v1/${bookId}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            })

            const apiDate = new Date(response.data.launchDate);
            const adjustedDate = apiDate.toISOString().split('T')[0];
            setId(response.data.id);
            setAuthor(response.data.author);
            setLaunchDate(adjustedDate);
            setPrice(response.data.price);
            setTitle(response.data.title);
        } catch (error) {
            alert('Error recovery Book! Try Again')
            navigate('/books')
        }
    }

    useEffect(() => {
        if(bookId === '0') return;
        else loadBook();
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [bookId])

    const navigate = useNavigate();

    async function editBook(e) {
        e.preventDefault();
        const data = {
            id,
            title,
            author,
            launchDate,
            price,
        }
        console.log(data)
        try {
            if(data.id === id) {
                await api.put(`/api/book/v1`, data, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                });
                
            }
            navigate('/books');
        } catch (error) {
            alert('Error while recording Book! Try again!')    
        }
    }
    return(
        <div className="new-book-container">
            <div className="content">
                <section className="form">
                    <img src={logo} alt="Logo" />
                    <h1>Edit a Book</h1>
                    <p>Enter the book information and click on 'Edit'! ### {bookId}</p>
                    <Link className="back-link" to="/books">
                       <FiArrowLeft size={16} color="#251FC5"></FiArrowLeft> 
                       Home
                    </Link>
                </section>
                <form onSubmit={editBook}>
                    <input
                        type="text"
                        placeholder="Id"
                        value={id} 
                        disabled
                    />
                    <input
                        type="text"
                        placeholder="Title"
                        value={title} 
                        onChange={e => setTitle(e.target.value)}
                    />
                    <input
                        type="text"
                        placeholder="Author"
                        value={author}
                        onChange={e => setAuthor(e.target.value)}
                    />
                    <input
                        type="date"
                        value={launchDate}
                        onChange={e => setLaunchDate(e.target.value)}
                    />
                    <input
                        type="text"
                        placeholder="Price"
                        value={price}
                        onChange={e => setPrice(e.target.value)}
                    />
                    <button type="submit" className="button">Edit</button>
                </form>
            </div>
        </div>
    );
}


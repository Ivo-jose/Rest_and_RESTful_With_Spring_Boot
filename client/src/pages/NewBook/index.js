//Lib
import React, {useState} from "react";
import { useNavigate, Link } from "react-router-dom";
import { FiArrowLeft } from 'react-icons/fi'
//CSS
import './styles.css'
//Images
import logo from '../../assets/logo.svg';
//API SERVICE
import api from '../../services/api';

export default function NewBook(){
    
    const [id, setId] = useState(null);
    const [author, setAuthor] = useState('');
    const [launchDate, setLaunchDate] = useState('');
    const [price, setPrice] = useState('');
    const [title, setTitle] = useState('');

    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    const navigate = useNavigate();

    async function createNewBook(e) {
        e.preventDefault();
        const data = {
            title,
            author,
            launchDate,
            price,
        }

        try {
            await api.post('/api/book/v1', data, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            });
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
                    <h1>add New Book</h1>
                    <p>Enter the book information and click on 'Add'!</p>
                    <Link className="back-link" to="/books">
                       <FiArrowLeft size={16} color="#251FC5"></FiArrowLeft> 
                       Home
                    </Link>
                </section>
                <form onSubmit={createNewBook}>
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
                    <button type="submit" className="button">Add</button>
                </form>
            </div>
        </div>
    );
}


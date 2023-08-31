package br.com.ivogoncalves.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ivogoncalves.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {}

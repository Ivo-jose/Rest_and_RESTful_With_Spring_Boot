package br.com.ivogoncalves.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ivogoncalves.controllers.BookController;
import br.com.ivogoncalves.data.vo.v1.BookVO;
import br.com.ivogoncalves.exceptions.RequiredObjectIsNullException;
import br.com.ivogoncalves.exceptions.ResourceNotFoundException;
import br.com.ivogoncalves.mapper.DozerMapper;
import br.com.ivogoncalves.model.Book;
import br.com.ivogoncalves.respositories.BookRepository;

@Service
public class BookServices {

	@Autowired
	BookRepository repository;
	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	public BookVO findById(Long id) {
		logger.info("Finding a Book...");
		var entity = repository.findById(id).orElseThrow(() 
				-> new ResourceNotFoundException("There are no records for this id! Id: " + id));
		BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
		
	}
	
	public List<BookVO> findAll() {
		logger.info("Finding all Books...");
		var persons = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
		persons.stream().forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getIdBook())).withSelfRel()));
		return persons;
	}
	
	public BookVO create(BookVO book) {
		if(book == null) throw new RequiredObjectIsNullException();
		logger.info("Creating a Book...");
		var entity = DozerMapper.parseObject(book, Book.class);
		BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(book.getIdBook())).withSelfRel());
		return vo;	
	}
	
	public BookVO update(BookVO book) {
		if(book == null) throw new RequiredObjectIsNullException();
		logger.info("Updating a Book...");
		var entity = repository.findById(book.getIdBook()).orElseThrow(() 
							  -> new ResourceNotFoundException("There are no records for this id! Id: " + book.getIdBook()));
		entity.setTitle(book.getTitle());
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(book.getIdBook())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleting a Book...");
		var entity = repository.findById(id).orElseThrow(() 
				   -> new ResourceNotFoundException("There are no records for this id! Id: " + id));
		repository.delete(entity);
	}
}
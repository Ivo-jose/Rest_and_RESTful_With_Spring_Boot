package br.com.ivogoncalves.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.ivogoncalves.data.vo.v1.BookVO;
import br.com.ivogoncalves.exceptions.RequiredObjectIsNullException;
import br.com.ivogoncalves.model.Book;
import br.com.ivogoncalves.respositories.BookRepository;
import br.com.ivogoncalves.services.BookServices;
import br.com.ivogoncalves.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {
	
	MockBook input;
	
	@InjectMocks
	private BookServices service;
	@Mock
	BookRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Book entity = input.mockEntity(1);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getIdBook());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Title Test1", result.getTitle());
		assertEquals("Author Test1", result.getAuthor());
		assertNotNull(result.getLaunchDate());
		assertEquals(101.0, result.getPrice());
	}

	@Test
	void testCreate() {
		Book entity = input.mockEntity(1);;
		Book persisted = entity;
		BookVO vo = input.mockVO(1);
		when(repository.save(entity)).thenReturn(persisted);
		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getIdBook());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Title Test1", result.getTitle());
		assertEquals("Author Test1", result.getAuthor());
		assertNotNull(result.getLaunchDate());
		assertEquals(101.0, result.getPrice());
	}
	
	
	@Test
	void testCreateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {service.create(null);});
		String expectedMessage = "Not allowed to persist a null object";
		assertTrue(exception.getMessage().contains(expectedMessage));
	}

	@Test
	void testUpdate() {
		Book entity = input.mockEntity(1);;
		Book persisted = entity;
		BookVO vo = input.mockVO(1);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		var result = service.update(vo);
		assertNotNull(result);
		assertNotNull(result.getIdBook());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Title Test1", result.getTitle());
		assertEquals("Author Test1", result.getAuthor());
		assertNotNull(result.getLaunchDate());
		assertEquals(101.0, result.getPrice());
	}
	
	@Test
	void testUpdateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {service.update(null);});
		String expectedMessage = "Not allowed to persist a null object";
		assertTrue(exception.getMessage().contains(expectedMessage));
	}

	@Test
	void testDelete() {
		Book entity = input.mockEntity(1);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		service.delete(1L);
	}

	@Test
	void testFindAll() {
		List<Book> list = input.mockEntityList();
		when(repository.findAll()).thenReturn(list);
		var books = service.findAll();
		assertNotNull(books);
		assertEquals(14, books.size());
		
		var bookOne = books.get(1);
		assertNotNull(bookOne);
		assertNotNull(bookOne.getIdBook());
		assertNotNull(bookOne.getLinks());
		assertTrue(bookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals("Title Test1", bookOne.getTitle());
		assertEquals("Author Test1", bookOne.getAuthor());
		assertNotNull(bookOne.getLaunchDate());
		assertEquals(101.0, bookOne.getPrice());
		
		var bookFour = books.get(4);
		assertNotNull(bookFour);
		assertNotNull(bookFour.getIdBook());
		assertNotNull(bookFour.getLinks());
		assertTrue(bookFour.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
		assertEquals("Title Test4", bookFour.getTitle());
		assertEquals("Author Test4", bookFour.getAuthor());
		assertNotNull(bookFour.getLaunchDate());
		assertEquals(104.0, bookFour.getPrice());
		
		var bookSeven = books.get(7);
		assertNotNull(bookSeven);
		assertNotNull(bookSeven.getIdBook());
		assertNotNull(bookSeven.getLinks());
		assertTrue(bookSeven.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));
		assertEquals("Title Test7", bookSeven.getTitle());
		assertEquals("Author Test7", bookSeven.getAuthor());
		assertNotNull(bookSeven.getLaunchDate());
		assertEquals(107.0, bookSeven.getPrice());
	}	
}

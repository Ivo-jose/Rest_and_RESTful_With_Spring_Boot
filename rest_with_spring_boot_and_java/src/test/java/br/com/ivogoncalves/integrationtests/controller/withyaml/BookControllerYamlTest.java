package br.com.ivogoncalves.integrationtests.controller.withyaml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.ivogoncalves.configs.TestConfigs;
import br.com.ivogoncalves.integrationtests.controller.withyaml.mapper.YamlMapper;
import br.com.ivogoncalves.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ivogoncalves.integrationtests.vo.AccountCredentialsVO;
import br.com.ivogoncalves.integrationtests.vo.BookVO;
import br.com.ivogoncalves.integrationtests.vo.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BookControllerYamlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static YamlMapper objectMapper;
	
	private static BookVO book;
	
	@BeforeAll
	public static void setUp() {
		objectMapper = new YamlMapper();
		book = new BookVO();
	}
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("ivolanda", "admin123");
		var accessToken = given()
							.config(RestAssuredConfig
									.config()
									.encoderConfig(EncoderConfig.encoderConfig()
													.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)))
							.basePath("/auth/signin")
								.port(TestConfigs.SERVER_PORT)
								.contentType(TestConfigs.CONTENT_TYPE_YAML)
								.accept(TestConfigs.CONTENT_TYPE_YAML)
							.body(user, objectMapper)
								.when()
							.post()
								.then()
								  .statusCode(200)
								  		.extract()
								  			.body()
								  			   .as(TokenVO.class, objectMapper).getAccessToken();
		specification = new RequestSpecBuilder()
							.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
							.setBasePath("/api/book/v1")
							.setPort(TestConfigs.SERVER_PORT)
								.addFilter(new RequestLoggingFilter(LogDetail.ALL))
								.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
							.build();
							
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockBook();
		var persistedBook = given().spec(specification)
				.config(RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
										.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)))
				 .contentType(TestConfigs.CONTENT_TYPE_YAML)
				 .accept(TestConfigs.CONTENT_TYPE_YAML)
				 .body(book, objectMapper)
				 .when()
				 	.post()
				 .then()
				 	.statusCode(200)
				 		.extract()
				 			.body()
				 			  .as(BookVO.class, objectMapper);
		book = persistedBook;
		assertNotNull(persistedBook.getId());
        assertNotNull(persistedBook.getTitle());
        assertNotNull(persistedBook.getAuthor());
        assertNotNull(persistedBook.getPrice());
        assertNotNull(persistedBook.getLaunchDate());
        assertTrue(persistedBook.getId() > 0);
        assertEquals(book.getId(), persistedBook.getId());
        assertEquals("Docker Deep Dive", persistedBook.getTitle());
        assertEquals("Nigel Poulton", persistedBook.getAuthor());
        assertEquals(55.99, persistedBook.getPrice());
	}
	
	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		book.setTitle("Docker Deep Dive - Updated");
		var persistedBook = given().spec(specification)
				.config(RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
										.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YAML)
				.accept(TestConfigs.CONTENT_TYPE_YAML)
				.body(book,objectMapper)
				.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
							.body()
							.as(BookVO.class, objectMapper);
		book = persistedBook;
		assertNotNull(persistedBook.getId());
        assertNotNull(persistedBook.getTitle());
        assertNotNull(persistedBook.getAuthor());
        assertNotNull(persistedBook.getPrice());
        assertNotNull(persistedBook.getLaunchDate());
        assertTrue(persistedBook.getId() > 0);
        assertEquals(book.getId(), persistedBook.getId());
        assertEquals("Docker Deep Dive - Updated", persistedBook.getTitle());
        assertEquals("Nigel Poulton", persistedBook.getAuthor());
        assertEquals(55.99, persistedBook.getPrice());
	}
	
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockBook();
		
		var persistedBook = given().spec(specification)
				.config(RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
										.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT))) 
				 .contentType(TestConfigs.CONTENT_TYPE_YAML)
				 .accept(TestConfigs.CONTENT_TYPE_YAML)
				 .pathParam("id", book.getId())
				 .when()
				 	.get("{id}")
				 .then()
				 	.statusCode(200)
				 		.extract()
				 			.body()
				 			.as(BookVO.class, objectMapper);
		book = persistedBook;
		assertNotNull(persistedBook.getId());
        assertNotNull(persistedBook.getTitle());
        assertNotNull(persistedBook.getAuthor());
        assertNotNull(persistedBook.getPrice());
        assertNotNull(persistedBook.getLaunchDate());
        assertTrue(persistedBook.getId() > 0);
        assertEquals(book.getId(), persistedBook.getId());
        assertEquals("Docker Deep Dive - Updated", persistedBook.getTitle());
        assertEquals("Nigel Poulton", persistedBook.getAuthor());
        assertEquals(55.99, persistedBook.getPrice());
	}
	
	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {
		given().spec(specification)
				.config(RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
										.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)))		
				 .contentType(TestConfigs.CONTENT_TYPE_YAML)
				 .accept(TestConfigs.CONTENT_TYPE_YAML)
				 .pathParam("id", book.getId())
				 .when()
				 	.delete("{id}")
				 .then()
				 	.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		var content = given().spec(specification)
				.config(RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
										.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)))
				 .contentType(TestConfigs.CONTENT_TYPE_YAML)
				 .accept(TestConfigs.CONTENT_TYPE_YAML)
				 .when()
				 	.get()
				 .then()
				 	.statusCode(200)
				 		.extract()
				 			.body()
				 			.as(BookVO[].class, objectMapper);
		List<BookVO> people = Arrays.asList(content);

		BookVO bookOne = people.get(0);
		assertNotNull(bookOne.getId());
        assertNotNull(bookOne.getTitle());
        assertNotNull(bookOne.getAuthor());
        assertNotNull(bookOne.getPrice());
        assertNotNull(bookOne.getLaunchDate());
        assertTrue(bookOne.getId() > 0);
        assertEquals(people.get(0).getId(), bookOne.getId());
        assertEquals("Working effectively with legacy code", bookOne.getTitle());
        assertEquals("Michael C. Feathers", bookOne.getAuthor());
        assertEquals(49.00, bookOne.getPrice());
		
		BookVO bookSix = people.get(5);
		assertNotNull(bookSix.getId());
        assertNotNull(bookSix.getTitle());
        assertNotNull(bookSix.getAuthor());
        assertNotNull(bookSix.getPrice());
        assertNotNull(bookSix.getLaunchDate());
        assertTrue(bookSix.getId() > 0);
        assertEquals(people.get(5).getId(), bookSix.getId());
        assertEquals("Refactoring", bookSix.getTitle());
        assertEquals("Martin Fowler e Kent Beck", bookSix.getAuthor());
        assertEquals(88.00, bookSix.getPrice());
	}

	@Test
	@Order(6)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/api/book/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		given().spec(specificationWithoutToken)
			.contentType(TestConfigs.CONTENT_TYPE_YAML)
			.when()
				.get()
			.then()
				.statusCode(403);
	}
	
	private void mockBook() {
        book.setTitle("Docker Deep Dive");
        book.setAuthor("Nigel Poulton");
        book.setPrice(Double.valueOf(55.99));
        book.setLaunchDate(new Date());
    }  
}
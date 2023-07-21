package br.com.ivogoncalves.integrationtests.controller.withyaml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
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
import br.com.ivogoncalves.integrationtests.vo.PersonVO;
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
public class PersonControllerYamlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static YamlMapper objectMapper;
	
	private static PersonVO person;
	
	@BeforeAll
	public static void setUp() {
		objectMapper = new YamlMapper();
		person = new PersonVO();
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
							.setBasePath("/api/person/v1")
							.setPort(TestConfigs.SERVER_PORT)
								.addFilter(new RequestLoggingFilter(LogDetail.ALL))
								.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
							.build();
							
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		var persistedPerson = given().spec(specification)
								.config(RestAssuredConfig
										.config()
										.encoderConfig(EncoderConfig.encoderConfig()
														.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)))
								 .contentType(TestConfigs.CONTENT_TYPE_YAML)
								 .accept(TestConfigs.CONTENT_TYPE_YAML)
								 .body(person, objectMapper)
								 .when()
								 	.post()
								 .then()
								 	.statusCode(200)
								 		.extract()
								 			.body()
								 			  .as(PersonVO.class, objectMapper);
		person = persistedPerson;
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertEquals(person.getId(), persistedPerson.getId());
		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet", persistedPerson.getLastName());
		assertEquals("Brasília, DF - Brasil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}
	
	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		person.setLastName("Piquet Souto Maior");
		var persistedPerson = given().spec(specification)
								.config(RestAssuredConfig
										.config()
										.encoderConfig(EncoderConfig.encoderConfig()
														.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)))
								.contentType(TestConfigs.CONTENT_TYPE_YAML)
								.accept(TestConfigs.CONTENT_TYPE_YAML)
								.body(person, objectMapper)
								.when()
									.post()
								.then()
									.statusCode(200)
										.extract()
											.body()
											.as(PersonVO.class, objectMapper);
		person = persistedPerson;
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertEquals(person.getId(), persistedPerson.getId());
		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
		assertEquals("Brasília, DF - Brasil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
	}
	
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var persistedPerson = given().spec(specification)
				 .config(RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
										.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT))) 
				 .contentType(TestConfigs.CONTENT_TYPE_YAML)
				 .accept(TestConfigs.CONTENT_TYPE_YAML)
				 .pathParam("id", person.getId())
				 .when()
				 	.get("{id}")
				 .then()
				 	.statusCode(200)
				 		.extract()
				 			.body()
				 			  .as(PersonVO.class, objectMapper);
		person = persistedPerson;
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertEquals(person.getId(), persistedPerson.getId());
		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
		assertEquals("Brasília, DF - Brasil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());
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
				 .pathParam("id", person.getId())
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
						     .as(PersonVO[].class, objectMapper);
		
		List<PersonVO> people = Arrays.asList(content);

		PersonVO personOne =  people.get(0);
		assertNotNull(personOne.getId());
		assertNotNull(personOne.getFirstName());
		assertNotNull(personOne.getLastName());
		assertNotNull(personOne.getAddress());
		assertNotNull(personOne.getGender());
		assertEquals(people.get(0).getId(), personOne.getId());
		assertEquals("Ayrton", personOne.getFirstName());
		assertEquals("Senna", personOne.getLastName());
		assertEquals("São Paulo - Vila Maria", personOne.getAddress());
		assertEquals("Male", personOne.getGender());
		
		PersonVO personSix = people.get(5);
		assertNotNull(personSix.getId());
		assertNotNull(personSix.getFirstName());
		assertNotNull(personSix.getLastName());
		assertNotNull(personSix.getAddress());
		assertNotNull(personSix.getGender());
		assertEquals(people.get(5).getId(), personSix.getId());
		assertEquals("Nelson", personSix.getFirstName());
		assertEquals("Mandela", personSix.getLastName());
		assertEquals("Mvezo - South Africa", personSix.getAddress());
		assertEquals("Male", personSix.getGender());
	}

	@Test
	@Order(6)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		given().spec(specificationWithoutToken)
			.config(RestAssuredConfig
					.config()
					.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, ContentType.TEXT)))
			.contentType(TestConfigs.CONTENT_TYPE_YAML)
			.when()
				.get()
			.then()
				.statusCode(403);
	}
	
	private void mockPerson() {
		person.setFirstName("Nelson");
		person.setLastName("Piquet");
		person.setAddress("Brasília, DF - Brasil");
		person.setGender("Male");
	}
}
package br.com.ivogoncalves.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.ivogoncalves.model.Person;

@Service
public class PersonServices {

	private static final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	public Person findById(String id) {
		logger.info("Finding one Person...");
		return new Person(counter.incrementAndGet(), "Ivo", "Souza", "Rua Guilherme Baer, 262 - Vila Ede", "Male");
	}
	
	public List<Person> findAll() {
		logger.info("Finding all People...");
		List<Person> persons = new ArrayList<>();
		for(int i=0; i<8; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}
		return persons;
	}
	
	public Person create(Person person) {
		logger.info("Creating one Person...");
		return person;
	}
	
	public Person update(Person person) {
		logger.info("Updating one Person...");
		return person;
	}
	
	public void delete(String id) {
		logger.info("Deleting one Person...");
	}

	private Person mockPerson(int i) {
		Person person = new Person();
		person.setId((long) (i+1));
		person.setFirstName("FirstName " + (i+1));
		person.setLastName("LastName " + (i+1));
		person.setAddress("Address " + (i+1));
		person.setGender((i%2 == 0)? "Male" : "Female");
		return person;
	}
}

package br.com.ivogoncalves.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ivogoncalves.exceptions.ResourceNotFoundException;
import br.com.ivogoncalves.model.Person;
import br.com.ivogoncalves.respositories.PersonRespository;

@Service
public class PersonServices {

	@Autowired
	PersonRespository repository;
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	public Person findById(Long id) {
		logger.info("Finding one Person...");
		return repository.findById(id).orElseThrow(() 
				-> new ResourceNotFoundException("There are no records for this id! Id: " + id));
		
	}
	
	public List<Person> findAll() {
		logger.info("Finding all People...");
		return repository.findAll();
	}
	
	public Person create(Person person) {
		logger.info("Creating one Person...");
		return repository.save(person);
	}
	
	public Person update(Person person) {
		logger.info("Updating one Person...");
		var entity = repository.findById(person.getId()).orElseThrow(() 
								   -> new ResourceNotFoundException("There are no records for this id! Id: " + person.getId()));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		return repository.save(entity);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one Person...");
		var entity = repository.findById(id).orElseThrow(() 
				   -> new ResourceNotFoundException("There are no records for this id! Id: " + id));
		repository.delete(entity);
	}
}
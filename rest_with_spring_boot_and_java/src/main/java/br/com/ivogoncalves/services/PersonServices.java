package br.com.ivogoncalves.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.ivogoncalves.controllers.PersonController;
import br.com.ivogoncalves.data.vo.v1.PersonVO;
import br.com.ivogoncalves.exceptions.RequiredObjectIsNullException;
import br.com.ivogoncalves.exceptions.ResourceNotFoundException;
import br.com.ivogoncalves.mapper.DozerMapper;
import br.com.ivogoncalves.model.Person;
import br.com.ivogoncalves.respositories.PersonRepository;

@Service
public class PersonServices {

	@Autowired
	PersonRepository repository;
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	public PersonVO findById(Long id) {
		logger.info("Finding one Person...");
		var entity = repository.findById(id).orElseThrow(() 
				-> new ResourceNotFoundException("There are no records for this id! Id: " + id));
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
		
	}
	
	public List<PersonVO> findAll() {
		logger.info("Finding all People...");
		var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getIdPerson())).withSelfRel()));
		return persons;
	}
	
	public PersonVO create(PersonVO person) {
		if(person == null) throw new RequiredObjectIsNullException();
		logger.info("Creating one Person...");
		var entity = DozerMapper.parseObject(person, Person.class);
		PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(person.getIdPerson())).withSelfRel());
		return vo;	
	}
	
	public PersonVO update(PersonVO person) {
		if(person == null) throw new RequiredObjectIsNullException();
		logger.info("Updating one Person...");
		var entity = repository.findById(person.getIdPerson()).orElseThrow(() 
							  -> new ResourceNotFoundException("There are no records for this id! Id: " + person.getIdPerson()));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(person.getIdPerson())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleting one Person...");
		var entity = repository.findById(id).orElseThrow(() 
				   -> new ResourceNotFoundException("There are no records for this id! Id: " + id));
		repository.delete(entity);
	}
}
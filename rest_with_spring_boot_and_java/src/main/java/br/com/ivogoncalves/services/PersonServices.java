package br.com.ivogoncalves.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ivogoncalves.data.vo.v1.PersonVO;
import br.com.ivogoncalves.data.vo.v2.PersonVOV2;
import br.com.ivogoncalves.exceptions.ResourceNotFoundException;
import br.com.ivogoncalves.mapper.DozerMapper;
import br.com.ivogoncalves.mapper.custom.PersonMapper;
import br.com.ivogoncalves.model.Person;
import br.com.ivogoncalves.respositories.PersonRespository;

@Service
public class PersonServices {

	@Autowired
	PersonRespository repository;
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	@Autowired
	PersonMapper mapper;
	
	public PersonVO findById(Long id) {
		logger.info("Finding one Person...");
		var entity = repository.findById(id).orElseThrow(() 
				-> new ResourceNotFoundException("There are no records for this id! Id: " + id));
		return DozerMapper.parseObject(entity, PersonVO.class);
		
	}
	
	public List<PersonVO> findAll() {
		logger.info("Finding all People...");
		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}
	
	public PersonVO create(PersonVO person) {
		logger.info("Creating one Person...");
		var entity = DozerMapper.parseObject(person, Person.class);
		return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
	}
	
	public PersonVOV2 createV2(PersonVOV2 person) {
		logger.info("Creating one Person with V2!...");
		var entity = mapper.convertVOV2ToEntity(person);
		var vo = mapper.convertEntityToVOV2(repository.save(entity));
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		logger.info("Updating one Person...");
		var entity = repository.findById(person.getId()).orElseThrow(() 
								  -> new ResourceNotFoundException("There are no records for this id! Id: " + person.getId()));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one Person...");
		var entity = repository.findById(id).orElseThrow(() 
				   -> new ResourceNotFoundException("There are no records for this id! Id: " + id));
		repository.delete(entity);
	}
}
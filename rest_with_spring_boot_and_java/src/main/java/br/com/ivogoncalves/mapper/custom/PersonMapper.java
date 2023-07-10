package br.com.ivogoncalves.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.ivogoncalves.data.vo.v2.PersonVOV2;
import br.com.ivogoncalves.model.Person;

@Service
public class PersonMapper {

	public PersonVOV2 convertEntityToVOV2(Person person) {
		PersonVOV2 voV2 = new PersonVOV2();
		voV2.setId(person.getId());
		voV2.setFirstName(person.getFirstName());
		voV2.setLastName(person.getLastName());
		voV2.setAddress(person.getAddress());
		voV2.setGender(person.getGender());
		voV2.setBirthDay(new Date());
		return voV2;
	}
	
	public Person convertVOV2ToEntity(PersonVOV2 voV2) {
		Person person = new Person();
		person.setId(voV2.getId());
		person.setFirstName(voV2.getFirstName());
		person.setLastName(voV2.getLastName());
		person.setAddress(voV2.getAddress());
		person.setGender(voV2.getGender());
		return person;
	}
}

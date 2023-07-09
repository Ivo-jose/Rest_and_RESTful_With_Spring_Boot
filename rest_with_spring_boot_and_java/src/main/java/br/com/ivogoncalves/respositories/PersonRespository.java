package br.com.ivogoncalves.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ivogoncalves.model.Person;

public interface PersonRespository extends JpaRepository<Person, Long> {}

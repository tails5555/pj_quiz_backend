package io.skhu.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.skhu.data.domain.Person;
import io.skhu.data.domain.User;
import io.skhu.data.repository.PersonRepository;

@Service
public class PersonService {
	@Autowired PersonRepository personRepository;
	public List<Person> findAll(){
		return personRepository.findAll();
	}
	public Person findOne(int id) {
		return personRepository.findOne(id);
	}
	public void create(Person person) {
		if(person.getId()>0) return;
		personRepository.save(person);
	}
	public void update(Person person) {
		if(person.getId()<=0) return;
		personRepository.save(person);
	}
	public long count() {
		return personRepository.count();
	}
	public Person findByUser(User user) {
		return personRepository.findByUser(user);
	}
}

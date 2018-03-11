package io.skhu.data.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import io.skhu.data.domain.Person;
import io.skhu.data.domain.User;
public interface PersonRepository extends JpaRepository<Person, Integer>{
	Person findByUser(User user);
}

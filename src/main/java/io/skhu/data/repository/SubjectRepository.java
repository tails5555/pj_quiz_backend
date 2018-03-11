package io.skhu.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.skhu.data.domain.Subject;
import io.skhu.data.domain.User;
public interface SubjectRepository extends JpaRepository<Subject, Integer>{
	List<Subject> findByIdNotIn(List<Integer> id);
	List<Subject> findByProfUser(User profUser);
	Subject findBySubDomain(String subDomain);
}

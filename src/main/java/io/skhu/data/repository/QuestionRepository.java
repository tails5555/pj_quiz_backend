package io.skhu.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.skhu.data.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	List<Question> findByIdInOrderByNoAsc(List<Integer> idList);
}

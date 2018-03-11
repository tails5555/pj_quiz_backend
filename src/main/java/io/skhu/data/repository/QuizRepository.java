package io.skhu.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.skhu.data.domain.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer>{
	Quiz findTopByProfUserIdAndSubjectIdOrderByIdDesc(int userId, int subjectId);
	List<Quiz> findBySubjectIdOrderByIdDesc(int subjectId);
}

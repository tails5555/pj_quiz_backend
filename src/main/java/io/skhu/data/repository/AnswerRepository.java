package io.skhu.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.skhu.data.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{
	long countByQuestionIdAndUserId(int questionId, int userId);
	List<Answer> findByUserIdAndQuestionIdIn(int userId, List<Integer> questionIdList);
	Answer findByUserIdAndQuestionId(int userId, int questionId);
}

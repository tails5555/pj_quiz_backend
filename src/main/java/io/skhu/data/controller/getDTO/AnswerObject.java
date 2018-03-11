package io.skhu.data.controller.getDTO;

import java.util.List;

import lombok.Data;

@Data
public class AnswerObject {
	String loginId;
	String userName;
	String gradeName;
	String title;
	String phone;
	int fullScore;
	List<AnswerWithStudent> answerWithStudent;
}

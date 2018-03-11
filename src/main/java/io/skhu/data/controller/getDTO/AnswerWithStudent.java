package io.skhu.data.controller.getDTO;

import java.util.Date;

import lombok.Data;

@Data
public class AnswerWithStudent {
	int answerId;
	String no;
	int fullScore;
	int resultScore;
	boolean bundled;
	String questionContext;
	String modelAnswer;
	String studentAnswer;
	String tagName;
	Date answerDate;
}

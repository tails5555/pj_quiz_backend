package io.skhu.data.controller.postDTO;

import lombok.Data;

@Data
public class QuestionCreateForm {
	String no;
	String context;
	String modelAnswer;
	boolean bundled;
	int fullScore;
}

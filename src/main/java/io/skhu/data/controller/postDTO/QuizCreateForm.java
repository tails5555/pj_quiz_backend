package io.skhu.data.controller.postDTO;

import java.util.List;

import lombok.Data;

@Data
public class QuizCreateForm {
	String title;
	int fullScore;
	String startTime;
	String limitedTime;
	List<QuestionCreateForm> questionList;
}

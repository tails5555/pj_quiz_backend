package io.skhu.data.controller.getDTO;

import java.util.Date;

import lombok.Data;

@Data
public class QuizListTitle {
	int id;
	String title;
	int fullScore;
	Date startTime;
	Date limitedTime;
	boolean isApplicate;
}

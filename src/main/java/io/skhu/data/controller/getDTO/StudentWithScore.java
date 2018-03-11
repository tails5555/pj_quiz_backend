package io.skhu.data.controller.getDTO;

import lombok.Data;

@Data
public class StudentWithScore {
	int id;
	String loginId;
	String userName;
	String gradeName;
	int totalScore;

}

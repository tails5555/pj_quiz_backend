package io.skhu.data.controller.getDTO;

import java.util.List;

import lombok.Data;

@Data
public class ScoringTableObject {
	String title;
	List<StudentWithScore> studentWithScore;
}

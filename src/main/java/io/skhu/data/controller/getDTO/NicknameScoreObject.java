package io.skhu.data.controller.getDTO;

import java.util.List;

import lombok.Data;

@Data
public class NicknameScoreObject {
	String title;
	List<NicknameWithScore> nicknameWithScore;
}

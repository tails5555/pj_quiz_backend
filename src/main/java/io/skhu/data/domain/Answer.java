package io.skhu.data.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;

	@Column(name="answerContext")
	String context;

	@ManyToOne
	@JoinColumn(name="userId")
	User user;

	@ManyToOne
	@JoinColumn(name="questionId")
	Question question;

	int resultScore;
	Boolean scoredComplete;

	Date answeredTime;
}

package io.skhu.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Question {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;

	String no;

	@Column(name="questionContext")
	String context;

	@JsonIgnore
	String modelAnswer;

	int fullScore;

	boolean bundled;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="quizId")
	Quiz quiz;
}

package io.skhu.data.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Quiz {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;

	String title;
	int fullScore;
	Date startTime;
	Date limitedTime;

	@ManyToOne
	@JoinColumn(name="userId")
	User profUser;

	@ManyToOne
	@JoinColumn(name="subjectId")
	Subject subject;

	@OneToMany(mappedBy="quiz", fetch=FetchType.LAZY)
	List<Question> questionList;
}

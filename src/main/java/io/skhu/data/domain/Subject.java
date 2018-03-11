package io.skhu.data.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(exclude={"assignUsers"})
public class Subject {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	String name;

	@ManyToOne
	@JoinColumn(name="semesterId")
	Semester semester;

	@ManyToOne
	@JoinColumn(name="userId")
	User profUser;

	String context;

	@Column(unique=true)
	String subDomain;

	@ManyToMany
	@JoinTable(name="userandsubject", joinColumns=@JoinColumn(name="subjectId"), inverseJoinColumns=@JoinColumn(name="userId"))
	List<User> assignUsers;
}

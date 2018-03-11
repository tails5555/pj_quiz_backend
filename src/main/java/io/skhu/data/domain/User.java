package io.skhu.data.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(exclude={"assignSubjects", "person"})
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;

	@Column(unique=true)
	String loginId;

	@JsonIgnore
	String password;

	String userType;

	@Column(unique=true)
	String nickName;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name="userandsubject", joinColumns=@JoinColumn(name="userId"), inverseJoinColumns=@JoinColumn(name="subjectId"))
	List<Subject> assignSubjects;

	@OneToOne(mappedBy="user", fetch=FetchType.LAZY)
	Person person;
}

package io.skhu.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Person {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;

	String name;

	@Column(unique=true)
	String email;

	@Column(name="phoneNumber", unique=true)
	String phone;

	@ManyToOne
	@JoinColumn(name="cityId")
	City city;

	@JsonIgnore
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	User user;

	@ManyToOne
	@JoinColumn(name="departmentId")
	Department department;

	@ManyToOne
	@JoinColumn(name="gradeId")
	Grade grade;

	@ManyToOne
	@JoinColumn(name="spotId")
	Spot spot;
}

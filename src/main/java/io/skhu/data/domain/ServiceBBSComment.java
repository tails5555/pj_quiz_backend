package io.skhu.data.domain;

import java.util.Date;

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
public class ServiceBBSComment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="postId")
	ServiceBBSPost serviceBBSPost;

	@ManyToOne
	@JoinColumn(name="userId")
	User user;

	String context;
	Date writtenDate;

}

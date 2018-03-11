package io.skhu.data.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="servicebbs")
public class ServiceBBSTitle {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;

	String name;

	@Column(unique=true)
	String subDomain;

	@JsonIgnore
	@OneToMany(mappedBy="serviceBBSTitle", fetch=FetchType.LAZY)
	List<ServiceBBSPost> serviceBBSPostList;
}

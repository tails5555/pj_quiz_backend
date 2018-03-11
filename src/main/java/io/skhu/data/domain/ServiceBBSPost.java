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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class ServiceBBSPost {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;

	String title;
	String context;

	@ManyToOne
	@JoinColumn(name="userId")
	User writer;

	@ManyToOne
	@JoinColumn(name="serviceBBSId")
	ServiceBBSTitle serviceBBSTitle;

	@JsonIgnore
	@OneToMany(mappedBy="serviceBBSPost", fetch=FetchType.LAZY)
	List<ServiceBBSComment> serviceBBSCommentList;

	@OneToMany(mappedBy="serviceBBSPost", fetch=FetchType.LAZY)
	List<ServiceBBSImage> serviceBBSImageList;

	@OneToMany(mappedBy="serviceBBSPost", fetch=FetchType.LAZY)
	List<ServiceBBSFile> serviceBBSFileList;

	Date writtenDate;
	int views;
}

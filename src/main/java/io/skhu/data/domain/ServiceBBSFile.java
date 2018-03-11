package io.skhu.data.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class ServiceBBSFile {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="postId")
	ServiceBBSPost serviceBBSPost;

	String fileName;
	int fileSize;

	@Basic(fetch=FetchType.LAZY)
	@Lob
	byte[] fileBytes;

	Date uploadDate;
}

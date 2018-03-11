package io.skhu.data.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.skhu.data.controller.postDTO.ServiceBBSCommentForm;
import io.skhu.data.controller.postDTO.ServiceBBSForm;
import io.skhu.data.domain.ServiceBBSComment;
import io.skhu.data.domain.ServiceBBSPost;
import io.skhu.data.domain.ServiceBBSTitle;
import io.skhu.data.domain.User;
import io.skhu.data.repository.ServiceBBSCommentRepository;
import io.skhu.data.repository.ServiceBBSFileRepository;
import io.skhu.data.repository.ServiceBBSImageRepository;
import io.skhu.data.repository.ServiceBBSPostRepository;
import io.skhu.data.repository.ServiceBBSTitleRepository;

@Service
public class ServiceBBSService {
	@Autowired ServiceBBSTitleRepository serviceBBSTitleRepository;
	@Autowired ServiceBBSPostRepository serviceBBSPostRepository;
	@Autowired ServiceBBSImageRepository serviceBBSImageRepository;
	@Autowired ServiceBBSFileRepository serviceBBSFileRepository;
	@Autowired ServiceBBSCommentRepository serviceBBSCommentRepository;

	public List<ServiceBBSTitle> getServiceBBSTitleList(){
		return serviceBBSTitleRepository.findAll();
	}

	public ServiceBBSTitle getPostTitleBySubDomain(String subDomain) {
		return serviceBBSTitleRepository.findBySubDomain(subDomain);
	}

	public List<ServiceBBSPost> findBySubDomain(String subDomain){
		ServiceBBSTitle getTitle=serviceBBSTitleRepository.findBySubDomain(subDomain);
		if(getTitle==null) return null;
		return serviceBBSPostRepository.findByServiceBBSTitleOrderByIdDesc(getTitle);
	}

	public void postCreate(User writer, ServiceBBSForm serviceBBSForm) {
		ServiceBBSPost serviceBBSPost=new ServiceBBSPost();
		ServiceBBSTitle serviceBBSTitle=serviceBBSTitleRepository.findOne(serviceBBSForm.getServiceBBSId());
		serviceBBSPost.setId(0);
		serviceBBSPost.setServiceBBSTitle(serviceBBSTitle);
		serviceBBSPost.setWriter(writer);
		serviceBBSPost.setWrittenDate(new Date());
		serviceBBSPost.setTitle(serviceBBSForm.getTitle());
		serviceBBSPost.setContext(serviceBBSForm.getContext());
		serviceBBSPostRepository.save(serviceBBSPost);
	}

	public boolean postDelete(User writer, int postId) {
		ServiceBBSPost deletePost=serviceBBSPostRepository.findOne(postId);
		if(writer.getNickName().equals(deletePost.getWriter().getNickName())) {
			serviceBBSPostRepository.delete(postId);
			return true;
		}else {
			return false;
		}

	}

	public void updateViews(int postId) {
		serviceBBSPostRepository.updateViews(postId);
	}

	public ServiceBBSPost getPostWithImagesAndFiles(int postId) {
		return serviceBBSPostRepository.findOne(postId);
	}

	public List<ServiceBBSComment> getCommentByPostId(int postId){
		ServiceBBSPost serviceBBSPost=serviceBBSPostRepository.findOne(postId);
		return serviceBBSCommentRepository.findByServiceBBSPostOrderByWrittenDateDesc(serviceBBSPost);
	}

	public boolean commentCreate(ServiceBBSCommentForm serviceBBSCommentForm, int postId, User user) {
		ServiceBBSPost post=serviceBBSPostRepository.findOne(postId);
		if(post==null) return false;
		ServiceBBSComment comment=new ServiceBBSComment();
		comment.setId(0);
		comment.setContext(serviceBBSCommentForm.getContext());
		comment.setWrittenDate(new Date());
		comment.setUser(user);
		comment.setServiceBBSPost(post);
		serviceBBSCommentRepository.save(comment);
		return true;
	}

	public boolean commentDelete(User writer, int commentId) {
		ServiceBBSComment deleteComment=serviceBBSCommentRepository.findOne(commentId);
		if(writer.getNickName().equals(deleteComment.getUser().getNickName())) {
			serviceBBSCommentRepository.delete(commentId);
			return true;
		}else {
			return false;
		}
	}
}

package io.skhu.data.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.skhu.data.controller.postDTO.ServiceBBSCommentForm;
import io.skhu.data.controller.postDTO.ServiceBBSForm;
import io.skhu.data.domain.ServiceBBSComment;
import io.skhu.data.domain.ServiceBBSPost;
import io.skhu.data.domain.ServiceBBSTitle;
import io.skhu.data.domain.User;
import io.skhu.data.service.ServiceBBSService;
import io.skhu.data.service.UserService;

@RestController
@RequestMapping("userAPI/common/serviceBBS")
public class ServiceBBSResources {
	@Autowired ServiceBBSService serviceBBSService;
	@Autowired UserService userService;
	@RequestMapping("getServiceBBSTitle")
	public List<ServiceBBSTitle> getServiceBBSTitle(){
		return serviceBBSService.getServiceBBSTitleList();
	}

	@RequestMapping("getServiceBBSPostTitle")
	public ServiceBBSTitle getServiceBBSPostTitle(@RequestParam("subDomain") String subDomain) {
		return serviceBBSService.getPostTitleBySubDomain(subDomain);
	}

	@RequestMapping("getServiceBBSPostList")
	public List<ServiceBBSPost> getServiceBBSPostList(@RequestParam("subDomain") String subDomain){
		return serviceBBSService.findBySubDomain(subDomain);
	}

	@RequestMapping(value="createPost", method=RequestMethod.POST)
	public String createPost(@RequestParam("userToken") String userToken, @RequestBody ServiceBBSForm serviceBBSForm) throws ServletException, UnsupportedEncodingException{
		User writer=userService.findByToken(userToken);
		if(writer==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		serviceBBSService.postCreate(writer, serviceBBSForm);
		return "서비스 게시판 게시물 등록이 완료되었습니다.";
	}

	@RequestMapping(value="deletePost", method=RequestMethod.DELETE)
	public String deletePost(@RequestParam("userToken") String userToken, @RequestParam("postId") int postId) throws ServletException, UnsupportedEncodingException {
		User writer=userService.findByToken(userToken);
		if(writer==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return serviceBBSService.postDelete(writer, postId) ? "게시물 삭제 작업이 완료되었습니다." : "작성자가 아니어서 삭제 작업을 진행하지 않았습니다. 다시 시도하시길 바랍니다.";
	}

	@RequestMapping(value="updateViews", method=RequestMethod.POST)
	public void updateViews(@RequestParam("userToken") String userToken, @RequestParam("postId") int postId) throws UnsupportedEncodingException, ServletException {
		User commonUser=userService.findByToken(userToken);
		if(commonUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		serviceBBSService.updateViews(postId);
	}

	@RequestMapping("getPost")
	public ServiceBBSPost getPost(@RequestParam("userToken") String userToken, @RequestParam("postId") int postId) throws UnsupportedEncodingException, ServletException {
		User commonUser=userService.findByToken(userToken);
		if(commonUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return serviceBBSService.getPostWithImagesAndFiles(postId);
	}

	@RequestMapping("getComment")
	public List<ServiceBBSComment> getCommentList(@RequestParam("userToken") String userToken, @RequestParam("postId") int postId) throws UnsupportedEncodingException, ServletException{
		User commonUser=userService.findByToken(userToken);
		if(commonUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return serviceBBSService.getCommentByPostId(postId);
	}

	@RequestMapping(value="createComment", method=RequestMethod.POST)
	public String createComment(@RequestParam("userToken") String userToken, @RequestParam("postId") int postId, @RequestBody ServiceBBSCommentForm serviceBBSCommentForm) throws ServletException, UnsupportedEncodingException {
		User commonUser=userService.findByToken(userToken);
		if(commonUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return serviceBBSService.commentCreate(serviceBBSCommentForm, postId, commonUser) ? "댓글 등록이 완료되었습니다." : "게시물 참고 도중 오류가 발생했습니다.";
	}

	@RequestMapping(value="deleteComment", method=RequestMethod.DELETE)
	public String deleteComment(@RequestParam("userToken") String userToken, @RequestParam("commentId") int commentId) throws UnsupportedEncodingException, ServletException{
		User commonUser=userService.findByToken(userToken);
		if(commonUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return serviceBBSService.commentDelete(commonUser, commentId) ? "댓글 삭제가 완료되었습니다." : "댓글 작성자가 불일치해서 삭제 작업을 진행하지 않았습니다.";
	}
}

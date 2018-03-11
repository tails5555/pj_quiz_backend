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

import io.skhu.data.controller.postDTO.SubjectForm;
import io.skhu.data.controller.postDTO.SubjectUpdateForm;
import io.skhu.data.domain.Subject;
import io.skhu.data.domain.User;
import io.skhu.data.service.SubjectService;
import io.skhu.data.service.UserService;
@RestController
@RequestMapping("userAPI/subject")
public class SubjectResources {
	@Autowired SubjectService subjectService;
	@Autowired UserService userService;

	@RequestMapping(value="student/application", method=RequestMethod.POST)
	public String subjectApplication(@RequestParam("token") String token, @RequestParam("subjectId") int id) throws UnsupportedEncodingException, ServletException {
		User user=userService.findByToken(token);
		if(user==null) throw new ServletException("유효하지 않은 학생입니다. 다시 시도하시길 바랍니다.");
		subjectService.userAssigningSubject(user, id);
		return "수강신청이 완료되었습니다.";
	}

	@RequestMapping("student/assignList")
	public List<Subject> studentSubjectList(@RequestParam("token") String token) throws ServletException, UnsupportedEncodingException{
		User user=userService.findByToken(token);
		if(user==null) throw new ServletException("유효하지 않은 학생입니다. 다시 시도하시길 바랍니다.");
		return subjectService.userAssignedSubjectList(user);
	}

	@RequestMapping("student/notAssignedList")
	public List<Subject> studentNotAssignedList(@RequestParam("token") String token) throws ServletException, UnsupportedEncodingException{
		User user=userService.findByToken(token);
		if(user==null) throw new ServletException("유효하지 않은 학생입니다. 다시 시도하시길 바랍니다.");
		return subjectService.userNotAssignedSubjectList(user);
	}

	@RequestMapping("professor/assignList")
	public List<Subject> professorAssignList(@RequestParam("token") String token) throws ServletException, UnsupportedEncodingException{
		User profUser=userService.findByToken(token);
		if(profUser==null) throw new ServletException("유효하지 않은 교수입니다. 다시 시도하시길 바랍니다.");
		return subjectService.professorAssignedSubjectList(profUser);
	}

	@RequestMapping(value="professor/addSubject", method=RequestMethod.POST)
	public String professorAddSubject(@RequestParam("token") String token, @RequestBody SubjectForm subjectForm) throws ServletException, UnsupportedEncodingException{
		User profUser=userService.findByToken(token);
		if(profUser==null) throw new ServletException("유효하지 않은 교수입니다. 다시 시도하시길 바랍니다.");
		subjectService.professorCreateSubject(profUser, subjectForm);
		return "담당 과목 등록이 완료되었습니다.";
	}

	@RequestMapping(value="professor/subDomainConfirm")
	public boolean professorSubDomainConfirm(@RequestParam("subDomain") String subDomain) {
		return subjectService.isSubDomainExist(subDomain);
	}

	@RequestMapping(value="professor/assignSubjectStudentList")
	public List<User> assignSubjectStudentList(@RequestParam("token") String token, @RequestParam("subDomain") String subDomain) throws UnsupportedEncodingException, ServletException{
		User profUser=userService.findByToken(token);
		if(profUser==null) throw new ServletException("유효하지 않은 교수입니다. 다시 시도하시길 바랍니다.");
		return subjectService.getStudentList(profUser, subDomain);
	}

	@RequestMapping(value="professor/assignStudentDelete", method=RequestMethod.POST)
	public String assignStudentDelete(@RequestParam("token") String token, @RequestParam("subDomain") String subDomain, @RequestParam("userId") int userId) throws UnsupportedEncodingException, ServletException{
		User profUser=userService.findByToken(token);
		if(profUser==null) throw new ServletException("유효하지 않은 교수입니다. 다시 시도하시길 바랍니다.");
		return subjectService.deleteStudent(profUser, subDomain, userId) ? "학생 삭제 작업이 완료되었습니다." : "담당 교수와 일치하지 않아서 삭제 작업을 완료하지 못했습니다.";
	}

	@RequestMapping(value="professor/subjectInfoUpdate", method=RequestMethod.POST)
	public String subjectInfoUpdate(@RequestParam("token") String token, @RequestParam("subDomain") String subDomain, @RequestBody SubjectUpdateForm subjectUpdateForm) throws UnsupportedEncodingException, ServletException{
		User profUser=userService.findByToken(token);
		if(profUser==null) throw new ServletException("유효하지 않은 교수입니다. 다시 시도하시길 바랍니다.");
		return subjectService.updateSubject(profUser, subDomain, subjectUpdateForm) ? "과목 내용 변경이 완료되었습니다." : "담당 교수와 일치하지 않아서 수정 작업을 완료하지 못했습니다.";
	}

	@RequestMapping(value="common/findBySubDomain")
	public Subject findBySubDomain(@RequestParam("token") String token, @RequestParam("subDomain") String subDomain) throws ServletException, UnsupportedEncodingException {
		User commonUser=userService.findByToken(token);
		if(commonUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return subjectService.findBySubDomain(subDomain);
	}

}

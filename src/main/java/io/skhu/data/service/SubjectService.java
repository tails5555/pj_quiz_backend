package io.skhu.data.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.skhu.data.controller.postDTO.SubjectForm;
import io.skhu.data.controller.postDTO.SubjectUpdateForm;
import io.skhu.data.domain.Semester;
import io.skhu.data.domain.Subject;
import io.skhu.data.domain.User;
import io.skhu.data.repository.SemesterRepository;
import io.skhu.data.repository.SubjectRepository;
import io.skhu.data.repository.UserRepository;

@Service
public class SubjectService {
	@Autowired UserRepository userRepository;
	@Autowired SubjectRepository subjectRepository;
	@Autowired SemesterRepository semesterRepository;
	public Subject findBySubDomain(String subDomain) {
		return subjectRepository.findBySubDomain(subDomain);
	}
	public List<Subject> userNotAssignedSubjectList(User user){
		List<Subject> userAssignedSubjectList=user.getAssignSubjects();
		List<Integer> userNotAssignedSubjectIdList=new ArrayList<Integer>();
		userNotAssignedSubjectIdList.add(-1);
		if(userAssignedSubjectList.size()!=0) {
			for(Subject s : userAssignedSubjectList) {
				userNotAssignedSubjectIdList.add(s.getId());
			}
		}
		Collections.sort(userNotAssignedSubjectIdList);
		return subjectRepository.findByIdNotIn(userNotAssignedSubjectIdList);
	}
	public List<Subject> userAssignedSubjectList(User user){
		return user.getAssignSubjects();
	}
	public void userAssigningSubject(User user, int subjectId) {
		List<Subject> userAssignedSubjectList=user.getAssignSubjects();
		Subject assigningSubject=subjectRepository.findOne(subjectId);
		userAssignedSubjectList.add(assigningSubject);
		user.setAssignSubjects(userAssignedSubjectList);
		userRepository.save(user);
	}
	public List<Subject> professorAssignedSubjectList(User profUser){
		return subjectRepository.findByProfUser(profUser);
	}
	public boolean isSubDomainExist(String subDomain) {
		Subject subject=subjectRepository.findBySubDomain(subDomain);
		return (subject!=null) ? true : false;
	}
	public void professorCreateSubject(User profUser, SubjectForm subjectForm) {
		Subject newSubject=new Subject();
		newSubject.setId(0);
		newSubject.setName(subjectForm.getName());
		newSubject.setSubDomain(subjectForm.getSubDomain());
		newSubject.setContext(subjectForm.getContext());
		newSubject.setProfUser(profUser);
		Semester semester=semesterRepository.findByCurrentDate(new Date());
		newSubject.setSemester(semester);
		subjectRepository.save(newSubject);
	}
	public List<User> getStudentList(User profUser, String subDomain){
		Subject assignSubject=subjectRepository.findBySubDomain(subDomain);
		if(profUser.getNickName().equals(assignSubject.getProfUser().getNickName())) {
			return assignSubject.getAssignUsers();
		}else {
			return null;
		}
	}
	public boolean deleteStudent(User profUser, String subDomain, int userId) {
		Subject assignSubject=subjectRepository.findBySubDomain(subDomain);
		if(profUser.getNickName().equals(assignSubject.getProfUser().getNickName())) {
			List<User> studentList=assignSubject.getAssignUsers();
			User student=userRepository.findOne(userId);
			studentList.remove(student);
			assignSubject.setAssignUsers(studentList);
			subjectRepository.save(assignSubject);
			return true;
		}else {
			return false;
		}
	}
	public boolean updateSubject(User profUser, String subDomain, SubjectUpdateForm subjectUpdateForm) {
		Subject subject=subjectRepository.findBySubDomain(subDomain);
		if(profUser.getNickName().equals(subject.getProfUser().getNickName())) {
			subject.setContext(subjectUpdateForm.getContext());
			subjectRepository.save(subject);
			return true;
		}else {
			return false;
		}
	}
}

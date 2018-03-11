package io.skhu.data.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.skhu.data.domain.Person;
import io.skhu.data.domain.Semester;
import io.skhu.data.domain.Subject;
import io.skhu.data.domain.User;
import io.skhu.data.repository.PersonRepository;
import io.skhu.data.repository.SemesterRepository;
import io.skhu.data.repository.SubjectRepository;
import io.skhu.data.repository.UserRepository;
@RestController
public class APIController {
	@Autowired PersonRepository personRepository;
	@Autowired UserRepository userRepository;
	@Autowired SubjectRepository subjectRepository;
	@Autowired SemesterRepository semesterRepository;
	@RequestMapping("personList")
	public List<Person> personList(){
		return personRepository.findAll();
	}
	@RequestMapping("userList")
	public List<Subject> userList(){
		return userRepository.findByNickName("자바의신").getAssignSubjects();
	}
	@RequestMapping("subjectList")
	public List<Subject> subjectList(){
		User user=userRepository.findByNickName("자바의신");
		List<Subject> list=user.getAssignSubjects();
		List<Integer> idList=new ArrayList<Integer>();
		if(list.size()==0) {
			idList.add(-1);
		}
		else {
			for(Subject s : list) {
				idList.add(s.getId());
			}
		}
		System.out.println(idList);
		return subjectRepository.findByIdNotIn(idList);
	}
	@RequestMapping("subjectList2")
	public List<Subject> subjectList2(){
		return subjectRepository.findAll();
	}
	@RequestMapping("userSaveSubject")
	public void userSaveSubject() {
		User user=userRepository.findByNickName("자바의신");
		List<Subject> list=user.getAssignSubjects();
		Subject subject1=subjectRepository.findOne(1);
		Subject subject2=subjectRepository.findOne(2);
		list.add(subject1);
		list.add(subject2);
		user.setAssignSubjects(list);
		userRepository.save(user);
	}
	@RequestMapping("currentSemester")
	public Semester findCurrentSemester() {
		return semesterRepository.findByCurrentDate(new Date());
	}
}

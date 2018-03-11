package io.skhu.data.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.skhu.data.controller.getDTO.AnswerObject;
import io.skhu.data.controller.getDTO.NicknameScoreObject;
import io.skhu.data.controller.getDTO.QuizListScoreTitle;
import io.skhu.data.controller.getDTO.QuizListTitle;
import io.skhu.data.controller.getDTO.ScoringTableObject;
import io.skhu.data.controller.postDTO.AnswerForm;
import io.skhu.data.controller.postDTO.QuizCreateForm;
import io.skhu.data.controller.postDTO.ScoringForm;
import io.skhu.data.domain.Quiz;
import io.skhu.data.domain.User;
import io.skhu.data.service.QuizService;
import io.skhu.data.service.UserService;

@RestController
@RequestMapping("userAPI/quiz")
public class QuizResource {
	@Autowired UserService userService;
	@Autowired QuizService quizService;
	@RequestMapping(value="professor/quizCreate", method=RequestMethod.POST)
	public String quizCreate(@RequestParam("token") String token, @RequestParam("subDomain") String subDomain, @RequestBody QuizCreateForm quizCreateForm) throws UnsupportedEncodingException, ServletException, ParseException {
		User profUser=userService.findByToken(token);
		if(profUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return quizService.quizCreate(profUser, subDomain, quizCreateForm) ? "퀴즈 등록 작업이 완료되었습니다." : "퀴즈 등록 작업 중에 오류가 발생했습니다. 다시 시도하시길 바랍니다.";
	}

	@RequestMapping("professor/quizList")
	public List<QuizListTitle> quizList(@RequestParam("token") String token, @RequestParam("subDomain") String subDomain) throws UnsupportedEncodingException, ServletException{
		User profUser=userService.findByToken(token);
		if(profUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return quizService.professorLoadQuizList(subDomain, profUser);
	}

	@RequestMapping("professor/scoringTable")
	public ScoringTableObject scoringTable(@RequestParam("token") String token, @RequestParam("quizId") int quizId) throws UnsupportedEncodingException, ServletException{
		User profUser=userService.findByToken(token);
		if(profUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return quizService.getScoringTableObject(profUser, quizId);
	}

	@RequestMapping("professor/answerWithStudent")
	public AnswerObject getAnswerWithStudent(@RequestParam("token") String token, @RequestParam("quizId") int quizId, @RequestParam("userId") int userId) throws UnsupportedEncodingException, ServletException {
		User profUser=userService.findByToken(token);
		if(profUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return quizService.answerWithStudent(userId, quizId);
	}

	@RequestMapping(value="professor/scoringQuiz", method=RequestMethod.POST)
	public String scoringQuiz(@RequestParam("token") String token, @RequestParam("studentNumber") String studentNumber, @RequestBody List<ScoringForm> scoringFormList) throws UnsupportedEncodingException, ServletException {
		User profUser=userService.findByToken(token);
		if(profUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return quizService.updateScoring(studentNumber, scoringFormList) ? "채점 작업이 완료되었습니다." : "채점 도중에 문제가 발생했습니다.";
	}

	@RequestMapping("student/quizList")
	public List<QuizListTitle> loadQuizList(@RequestParam("token") String token, @RequestParam("subDomain") String subDomain) throws UnsupportedEncodingException, ServletException{
		User studentUser=userService.findByToken(token);
		if(studentUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return quizService.loadQuizList(subDomain, studentUser);
	}

	@RequestMapping("student/fetchQuiz")
	public Quiz loadQuiz(@RequestParam("token") String token, @RequestParam("subDomain") String subDomain, @RequestParam("quizId") int quizId) throws UnsupportedEncodingException, ServletException {
		User studentUser=userService.findByToken(token);
		if(studentUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return quizService.loadQuiz(subDomain, studentUser, quizId);
	}

	@RequestMapping("student/fetchQuizConfirm")
	public AnswerObject loadQuizConfirm(@RequestParam("token") String token, @RequestParam("quizId") int quizId) throws UnsupportedEncodingException, ServletException{
		User studentUser=userService.findByToken(token);
		if(studentUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return quizService.answerWithStudent(studentUser.getId(), quizId);
	}

	@RequestMapping(value="student/createAnswer", method=RequestMethod.POST)
	public String createQuiz(@RequestParam("token") String token, @RequestBody List<AnswerForm> answerFormList) throws UnsupportedEncodingException, ServletException {
		User studentUser=userService.findByToken(token);
		if(studentUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		quizService.createAnswerList(studentUser, answerFormList);
		return "퀴즈 답안 등록이 완료되었습니다.";
	}

	@RequestMapping(value="student/quizResultTitle")
	public List<QuizListScoreTitle> getQuizResultTitle(@RequestParam("token") String token, @RequestParam("subDomain") String subDomain) throws UnsupportedEncodingException, ServletException {
		User studentUser=userService.findByToken(token);
		if(studentUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return quizService.getQuizListScoreTitle(studentUser, subDomain);
	}

	@RequestMapping(value="student/quizResultWithNickName")
	public NicknameScoreObject getQuizResultWithNickName(@RequestParam("token") String token, @RequestParam("subDomain") String subDomain, @RequestParam("quizId") int quizId) throws ServletException, UnsupportedEncodingException{
		User studentUser=userService.findByToken(token);
		if(studentUser==null) throw new ServletException("유효하지 않은 사용자입니다. 다시 시도하시길 바랍니다.");
		return quizService.getNickNameWithScore(subDomain, quizId);
	}

}

package io.skhu.data.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.skhu.data.controller.getDTO.AnswerObject;
import io.skhu.data.controller.getDTO.AnswerWithStudent;
import io.skhu.data.controller.getDTO.NicknameScoreObject;
import io.skhu.data.controller.getDTO.NicknameWithScore;
import io.skhu.data.controller.getDTO.QuizListScoreTitle;
import io.skhu.data.controller.getDTO.QuizListTitle;
import io.skhu.data.controller.getDTO.ScoringTableObject;
import io.skhu.data.controller.getDTO.StudentWithScore;
import io.skhu.data.controller.postDTO.AnswerForm;
import io.skhu.data.controller.postDTO.QuestionCreateForm;
import io.skhu.data.controller.postDTO.QuizCreateForm;
import io.skhu.data.controller.postDTO.ScoringForm;
import io.skhu.data.domain.Answer;
import io.skhu.data.domain.Question;
import io.skhu.data.domain.Quiz;
import io.skhu.data.domain.Subject;
import io.skhu.data.domain.User;
import io.skhu.data.repository.AnswerRepository;
import io.skhu.data.repository.QuestionRepository;
import io.skhu.data.repository.QuizRepository;
import io.skhu.data.repository.SubjectRepository;
import io.skhu.data.repository.UserRepository;

@Service
public class QuizService {
	@Autowired UserRepository userRepository;
	@Autowired QuizRepository quizRepository;
	@Autowired QuestionRepository questionRepository;
	@Autowired AnswerRepository answerRepository;
	@Autowired SubjectRepository subjectRepository;

	public boolean quizCreate(User profUser, String subDomain, QuizCreateForm quizCreateForm) throws ParseException {
		Subject subject=subjectRepository.findBySubDomain(subDomain);
		if(subject.getProfUser().getNickName().equals(profUser.getNickName())) {
			Quiz quiz=new Quiz();
			quiz.setId(0);
			quiz.setFullScore(quizCreateForm.getFullScore());
			quiz.setTitle(quizCreateForm.getTitle());
			quiz.setProfUser(profUser);
			quiz.setSubject(subject);
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date startTime=format.parse(quizCreateForm.getStartTime());
			Date limitedTime=format.parse(quizCreateForm.getLimitedTime());
			quiz.setStartTime(startTime);
			quiz.setLimitedTime(limitedTime);
			quizRepository.save(quiz);

			Quiz currentQuiz=quizRepository.findTopByProfUserIdAndSubjectIdOrderByIdDesc(profUser.getId(), subject.getId());
			for(QuestionCreateForm qcs : quizCreateForm.getQuestionList()) {
				Question question=new Question();
				question.setId(0);
				question.setNo(qcs.getNo());
				question.setQuiz(currentQuiz);
				question.setBundled(qcs.isBundled());
				question.setFullScore(qcs.getFullScore());
				question.setModelAnswer(qcs.getModelAnswer());
				question.setContext(qcs.getContext());
				questionRepository.save(question);
			}
			return true;
		}else {
			return false;
		}
	}

	public ScoringTableObject getScoringTableObject(User profUser, int quizId) {
		ScoringTableObject sto=new ScoringTableObject();
		List<StudentWithScore> scoreList=new ArrayList<StudentWithScore>();
		Quiz quiz=quizRepository.findOne(quizId);
		sto.setTitle(quiz.getTitle());
		Subject subject=quiz.getSubject();
		List<User> studentUser=subject.getAssignUsers();
		List<Question> questionList=quiz.getQuestionList();
		List<Integer> idList=new ArrayList<Integer>();
		for(Question q : questionList) {
			idList.add(q.getId());
		}
		for(User u : studentUser) {
			StudentWithScore newSws=new StudentWithScore();
			newSws.setId(u.getId());
			newSws.setLoginId(u.getLoginId());
			newSws.setUserName(u.getPerson().getName());
			newSws.setGradeName(u.getPerson().getGrade().getName());
			int tmpTotal=0;
			for(Answer a : answerRepository.findByUserIdAndQuestionIdIn(u.getId(), idList)) {
				if(a.getScoredComplete()) {
					tmpTotal+=a.getResultScore();
				}
			}
			newSws.setTotalScore(tmpTotal);
			scoreList.add(newSws);
		}
		sto.setStudentWithScore(scoreList);
		return sto;
	}

	public List<QuizListTitle> loadQuizList(String subDomain, User studentUser){
		Subject subject=subjectRepository.findBySubDomain(subDomain);
		if(subject.getAssignUsers().contains(studentUser)) {
			List<Quiz> quizList=quizRepository.findBySubjectIdOrderByIdDesc(subject.getId());
			List<QuizListTitle> quizTitle=new ArrayList<QuizListTitle>();
			for(Quiz q : quizList) {
				long questionCount=0;
				List<Integer> questionIdList=new ArrayList<Integer>();
				for(Question question : q.getQuestionList()) {
					questionIdList.add(question.getId());
					if(!question.isBundled()) questionCount=questionCount+1;
				}
				QuizListTitle title=new QuizListTitle();
				List<Answer> userAnswer=answerRepository.findByUserIdAndQuestionIdIn(studentUser.getId(), questionIdList);
				if(userAnswer.size()==questionCount) {
					title.setApplicate(true);
				}else {
					title.setApplicate(false);
				}
				title.setId(q.getId());
				title.setFullScore(q.getFullScore());
				title.setStartTime(q.getStartTime());
				title.setLimitedTime(q.getLimitedTime());
				title.setTitle(q.getTitle());
				quizTitle.add(title);
			}
			return quizTitle;
		}else {
			return new ArrayList<QuizListTitle>();
		}
	}

	public List<QuizListTitle> professorLoadQuizList(String subDomain, User profUser){
		Subject subject=subjectRepository.findBySubDomain(subDomain);
		if(subject.getProfUser().getNickName().equals(profUser.getNickName())) {
			List<Quiz> quizList=quizRepository.findBySubjectIdOrderByIdDesc(subject.getId());
			List<QuizListTitle> quizTitle=new ArrayList<QuizListTitle>();
			for(Quiz q : quizList) {
				QuizListTitle title=new QuizListTitle();
				title.setId(q.getId());
				title.setFullScore(q.getFullScore());
				title.setStartTime(q.getStartTime());
				title.setLimitedTime(q.getLimitedTime());
				title.setTitle(q.getTitle());
				quizTitle.add(title);
			}
			return quizTitle;
		}else {
			return new ArrayList<QuizListTitle>();
		}
	}

	public Quiz loadQuiz(String subDomain, User studentUser, int quizId) {
		Subject subject=subjectRepository.findBySubDomain(subDomain);
		if(subject.getAssignUsers().contains(studentUser)) {
			Quiz quiz=quizRepository.findOne(quizId);
			List<Question> questionList=quiz.getQuestionList();
			List<Integer> idList=new ArrayList<Integer>();
			for(Question q : questionList) {
				idList.add(q.getId());
			}
			List<Question> noAscQuizList=questionRepository.findByIdInOrderByNoAsc(idList);
			quiz.setQuestionList(noAscQuizList);
			return quiz;
		}else return null;
	}

	public void createAnswerList(User studentUser, List<AnswerForm> answerFormList) {
		for(AnswerForm answerForm : answerFormList) {
			Answer answer=new Answer();
			answer.setId(0);
			answer.setAnsweredTime(new Date());
			Question question=questionRepository.findOne(answerForm.getQuestionId());
			answer.setQuestion(question);
			answer.setUser(studentUser);
			answer.setContext(answerForm.getContext());
			answer.setResultScore(0);
			answer.setScoredComplete(false);
			answerRepository.save(answer);
		}
	}

	public AnswerObject answerWithStudent(int userId, int quizId) {
		AnswerObject answerObject=new AnswerObject();
		User studentUser=userRepository.findOne(userId);
		if(studentUser!=null) {
			answerObject.setLoginId(studentUser.getLoginId());
			answerObject.setUserName(studentUser.getPerson().getName());
			answerObject.setGradeName(studentUser.getPerson().getGrade().getName());
			answerObject.setPhone(studentUser.getPerson().getPhone());
			List<AnswerWithStudent> answerWithStudent=new ArrayList<AnswerWithStudent>();
			Quiz quiz=quizRepository.findOne(quizId);
			answerObject.setFullScore(quiz.getFullScore());
			answerObject.setTitle(quiz.getTitle());
			List<Question> questionList=quiz.getQuestionList();
			List<Integer> idList=new ArrayList<Integer>();
			for(Question q : questionList) {
				idList.add(q.getId());
			}
			List<Question> noAscQuizList=questionRepository.findByIdInOrderByNoAsc(idList);
			for(Question q : noAscQuizList) {
				AnswerWithStudent aws=new AnswerWithStudent();
				aws.setNo(q.getNo());
				aws.setQuestionContext(q.getContext());
				if(!q.isBundled()) {
					aws.setFullScore(q.getFullScore());
					aws.setModelAnswer(q.getModelAnswer());
					Answer stdAnswer=answerRepository.findByUserIdAndQuestionId(userId, q.getId());
					if(stdAnswer!=null) {
						aws.setAnswerId(stdAnswer.getId());
						aws.setResultScore(stdAnswer.getResultScore());
						aws.setTagName("scoring"+stdAnswer.getId());
						aws.setStudentAnswer(stdAnswer.getContext());
						aws.setAnswerDate(stdAnswer.getAnsweredTime());
					}else {
						aws.setTagName(null);
						aws.setStudentAnswer("<p>퀴즈에 응답을 하지 못했습니다.</p>");
						aws.setAnswerDate(new Date());
					}
				}else {
					aws.setBundled(true);
					aws.setFullScore(0);
					aws.setStudentAnswer("<p>보기 문제이므로 학생 답안은 없습니다.</p>");
				}
				answerWithStudent.add(aws);
			}
			answerObject.setAnswerWithStudent(answerWithStudent);
		}
		return answerObject;
	}

	public boolean updateScoring(String loginId, List<ScoringForm> scoringFormList) {
		User studentUser=userRepository.findByLoginId(loginId);
		if(studentUser==null) return false;
		for(ScoringForm sf : scoringFormList) {
			Answer answer=answerRepository.findOne(sf.getAnswerId());
			if(answer.getUser().getLoginId().equals(loginId)) {
				answer.setScoredComplete(true);
				answer.setResultScore(sf.getScore());
				answerRepository.save(answer);
			}
		}
		return true;
	}

	public List<QuizListScoreTitle> getQuizListScoreTitle(User studentUser, String subDomain){
		Subject subject=subjectRepository.findBySubDomain(subDomain);
		List<QuizListScoreTitle> titleList=new ArrayList<QuizListScoreTitle>();
		if(subject!=null) {
			List<Quiz> quizList=quizRepository.findBySubjectIdOrderByIdDesc(subject.getId());
			for(Quiz q : quizList) {
				QuizListScoreTitle newTitle=new QuizListScoreTitle();
				newTitle.setId(q.getId());
				newTitle.setTitle(q.getTitle());
				newTitle.setFullScore(q.getFullScore());
				List<Question> questionList=q.getQuestionList();
				List<Integer> idList=new ArrayList<Integer>();
				for(Question qu : questionList) {
					idList.add(qu.getId());
				}
				int tmpTotal=0;
				for(Answer a : answerRepository.findByUserIdAndQuestionIdIn(studentUser.getId(), idList)) {
					if(a.getScoredComplete()) {
						tmpTotal+=a.getResultScore();
					}
				}
				newTitle.setTotalScore(tmpTotal);
				titleList.add(newTitle);
			}
			return titleList;
		}
		return titleList;
	}

	public NicknameScoreObject getNickNameWithScore(String subDomain, int quizId){
		NicknameScoreObject nso=new NicknameScoreObject();
		Subject subject=subjectRepository.findBySubDomain(subDomain);
		List<User> assignUser=subject.getAssignUsers();
		List<NicknameWithScore> nicknameWithScore=new ArrayList<NicknameWithScore>();
		Quiz quiz=quizRepository.findOne(quizId);
		List<Question> questionList=quiz.getQuestionList();
		List<Integer> idList=new ArrayList<Integer>();
		for(Question qu : questionList) {
			idList.add(qu.getId());
		}
		nso.setTitle(quiz.getTitle());
		for(User u : assignUser) {
			NicknameWithScore nws=new NicknameWithScore();
			nws.setNickName(u.getNickName());
			int tmpTotal=0;
			for(Answer a : answerRepository.findByUserIdAndQuestionIdIn(u.getId(), idList)) {
				if(a.getScoredComplete()) {
					tmpTotal+=a.getResultScore();
				}
			}
			nws.setScore(tmpTotal);
			nicknameWithScore.add(nws);
		}
		nso.setNicknameWithScore(nicknameWithScore);
		return nso;
	}
}

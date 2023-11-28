package com.example.questionnaire;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.questionnaire.Repository.QuestionDao;
import com.example.questionnaire.Repository.QuestionnaireDao;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.Questionnaire;
import com.example.questionnaire.service.ifs.QuizService;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizVo;

@SpringBootTest
public class QuizServiceTest {

	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	
	@Test
	public void createTest() {
		Questionnaire questionnaire  = new Questionnaire("test1","test",false,LocalDate.of(2023, 12, 10),LocalDate.of(2023, 12, 31));
		List<Question> questionList = new ArrayList<>();
		Question q1 = new Question(1,"test_question_1","single",false,"AAA;BBB;CCC");
		Question q2 = new Question(2,"test_question_2","multi",false,"10;20;30");
		Question q3 = new Question(3,"test_question_3","text",false,"ABC");
		questionList.addAll(Arrays.asList(q1,q2,q3));
		QuizReq req = new QuizReq(questionnaire,questionList);
		QuizRes res = quizService.create(req);
		Assert.isTrue(res.getRtnCode().getCode()==200,"crate error");
	}
	
	@Test
	public void updateTest() {
		Questionnaire questionnaire  = new Questionnaire(8,"test1update","test",false,LocalDate.of(2023, 11, 25),LocalDate.of(2023, 11, 30));
		List<Question> questionList = new ArrayList<>();
		Question q1 = new Question(1,8,"test_update","single",false,"AAA;BBB;CCC");
		Question q2 = new Question(2,8,"test_update","multi",false,"10;20;30");
		Question q3 = new Question(3,8,"test_update","text",false,"ABC");
		questionList.addAll(Arrays.asList(q1,q2,q3));
		QuizReq req = new QuizReq(questionnaire,questionList);
		QuizRes res = quizService.update(req);
		Assert.isTrue(res.getRtnCode().getCode()==200,"update error");
	}
	
	
	@Test
	public void deleteQuestionnaireTest() {
		List<Integer>qnIdList = Arrays.asList(23);
		QuizRes res = quizService.deleteQustionnaire(qnIdList);
		
		Assert.isTrue(res.getRtnCode().getCode()==200,"delete error");
	}
	
	
	@Test
	public void deleteQuestionTest() {
		List<Integer>quIdList = Arrays.asList(1,2,3);
		QuizRes res = quizService.deleteQustion(23, quIdList);
		
		Assert.isTrue(res.getRtnCode().getCode()==200,"delete error");
	}
	
	@Transactional
	@Test
	public void searchTest() {
		QuizRes res = quizService.search("test",null,null);
		List<QuizVo> quizVoList = res.getQuizVoList();
		for(QuizVo item:quizVoList) {
			List<Question> question = item.getQuestionList();
			for(Question item1:question) {
				System.out.println(item1.getqTitle());
			}
			System.out.println(item.getQuestionnaire().getTitle());
		}
		Assert.isTrue(res.getRtnCode().getCode()==200,"search error");
	}
		
		
	@Test
	public void insertTest() {
//		int res = questionnaireDao.insertData("qa_03", "qa_01_test", false, LocalDate.of(2023, 11, 24), LocalDate.of(2023, 12, 01));
//		System.out.println(res);//pk存在無法insert
		
		int res = questionDao.insert("ttt", "multi", false, "t1;t2;t3");
		System.out.println(res);
	}
	
	@Test
	public void update1Test() {
		int res = questionnaireDao.update(5, "qn_007","qn007_Test");
		System.out.println(res);
	}
	
	@Test
	public void updateDateTest() {
		int res = questionnaireDao.updateData(2, "qn_008","qn008_Test",LocalDate.of(2023, 11, 27));
		System.out.println(res);
	}
	
	@Test
	public void limitTest() {
		List<Questionnaire>res = questionnaireDao.findWithLimitAndStartPosition(0,3);
		for(Questionnaire item:res) {
			System.out.println(item.getId());
		}
		res.forEach(item ->{
			System.out.println(item.getId());
		});
	}
	
	@Test
	public void likeTest() {
		List<Questionnaire>res = questionnaireDao.searchTitleLike("test");
		System.out.println(res.size());
	}
	
	
	}

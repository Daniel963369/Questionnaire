package com.example.questionnaire.controller;



import java.time.LocalDate;

import java.util.List;

<<<<<<< HEAD
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> 10910a2beb2375331612868bc75e426d9689010e
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionnaire.service.ifs.QuizService;
<<<<<<< HEAD
import com.example.questionnaire.vo.QuestionRes;
=======
>>>>>>> 10910a2beb2375331612868bc75e426d9689010e
import com.example.questionnaire.vo.QuestionnaireRes;
import com.example.questionnaire.vo.QuizReq;
import com.example.questionnaire.vo.QuizRes;
import com.example.questionnaire.vo.QuizSearchReq;


@RestController
@CrossOrigin
public class QuizController {
	
	@Autowired
	private QuizService quizService;
	
	@PostMapping(value = "api/quiz/create")
<<<<<<< HEAD
	public QuizRes create(@RequestBody  QuizReq req) {
=======
	public QuizRes create(@RequestBody QuizReq req) {
>>>>>>> 10910a2beb2375331612868bc75e426d9689010e
		return quizService.create(req);
	}
	
	@GetMapping(value = "api/quiz/search")
	public QuizRes search(@RequestParam(name = "title",required = false)String title,
<<<<<<< HEAD
			@RequestParam(name = "startDate",required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam (name = "endDate",required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate endDate) {
=======
			@RequestParam(name = "startDate",required = false)LocalDate startDate,
			@RequestParam (name = "endDate",required = false)LocalDate endDate) {
>>>>>>> 10910a2beb2375331612868bc75e426d9689010e
		String fillterTitle = StringUtils.hasText(title)? title:"";
		LocalDate fillterStartDate =startDate != null ? startDate:LocalDate.of(1971,1,1);
		LocalDate fillterEndDate = endDate != null? endDate: LocalDate.of(2099,12,31);
		return quizService.search(fillterTitle,fillterStartDate,fillterEndDate);
	}
	
<<<<<<< HEAD
	@GetMapping(value = "api/quiz/searchQuestionList")
	public QuestionRes searchQuestionList(@RequestParam int qnId) {
		return quizService.searchQuestionList(qnId);
	}
	
=======
>>>>>>> 10910a2beb2375331612868bc75e426d9689010e
	@PostMapping(value = "api/quiz/update")
	public QuizRes update(@RequestBody QuizReq req) {
		
		return quizService.update(req);
	}
	
	@PostMapping(value = "api/quiz/deleteQuestionnaire")
	public QuizRes deleteQustionnaire(@RequestBody List<Integer> qnIdList) {
		
		return quizService.deleteQustionnaire(qnIdList);
	}
	
	@PostMapping(value = "api/quiz/deleteQuestion")
	public QuizRes deleteQustion(@RequestBody int qnId,List<Integer> quIdList) {
		
		return quizService.deleteQustion(qnId,quIdList);
	}
	
<<<<<<< HEAD
	
	
=======
>>>>>>> 10910a2beb2375331612868bc75e426d9689010e
}

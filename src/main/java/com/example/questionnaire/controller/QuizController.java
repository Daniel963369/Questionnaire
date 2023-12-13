package com.example.questionnaire.controller;



import java.time.LocalDate;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionnaire.service.ifs.QuizService;
import com.example.questionnaire.vo.QuestionRes;
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
	public QuizRes create(@RequestBody  QuizReq req) {
		return quizService.create(req);
	}
	
	@GetMapping(value = "api/quiz/search")
	public QuizRes search(@RequestParam(name = "title",required = false)String title,
			@RequestParam(name = "startDate",required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam (name = "endDate",required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate endDate) {
		String fillterTitle = StringUtils.hasText(title)? title:"";
		LocalDate fillterStartDate =startDate != null ? startDate:LocalDate.of(1971,1,1);
		LocalDate fillterEndDate = endDate != null? endDate: LocalDate.of(2099,12,31);
		return quizService.search(fillterTitle,fillterStartDate,fillterEndDate);
	}
	
	@GetMapping(value = "api/quiz/searchQuestionList")
	public QuestionRes searchQuestionList(@RequestParam int qnId) {
		return quizService.searchQuestionList(qnId);
	}
	
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
	
	
	
}

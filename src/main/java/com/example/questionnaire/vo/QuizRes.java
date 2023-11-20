package com.example.questionnaire.vo;

import java.util.List;

import com.example.questionnaire.constants.RtnCode;

public class QuizRes {
	
	private RtnCode rtnCode;
	
	private List<QuizVo> quizVoList;

	public QuizRes() {
		super();
	}
	
	

	public QuizRes(RtnCode rtnCode) {
		super();
		this.rtnCode = rtnCode;
	}
	
	



	public QuizRes(List<QuizVo> quizVoList,RtnCode rtnCode) {
		super();
		this.quizVoList = quizVoList;
		this.rtnCode = rtnCode;
	}



	public RtnCode getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(RtnCode rtnCode) {
		this.rtnCode = rtnCode;
	}



	public List<QuizVo> getQuizVoList() {
		return quizVoList;
	}



	public void setQuizVoList(List<QuizVo> quizVoList) {
		this.quizVoList = quizVoList;
	}
	
	
	
	
}

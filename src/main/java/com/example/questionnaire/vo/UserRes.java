package com.example.questionnaire.vo;

import java.util.List;

import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.User;

public class UserRes {

	
	private List <QuizVo> quizVoList;
	
	private RtnCode rtnCode;
	
	private List<User> userList;

	public UserRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserRes(List<QuizVo> quizVoList, RtnCode rtnCode, List<User> userList) {
		super();
		this.quizVoList = quizVoList;
		this.rtnCode = rtnCode;
		this.userList = userList;
	}
	



	public UserRes(RtnCode rtnCode) {
		super();
		this.rtnCode = rtnCode;
	}

	public UserRes(RtnCode rtnCode, List<User> userList) {
		super();
		this.rtnCode = rtnCode;
		this.userList = userList;
	}

	public List<QuizVo> getQuizVoList() {
		return quizVoList;
	}

	public void setQuizVoList(List<QuizVo> quizVoList) {
		this.quizVoList = quizVoList;
	}

	public RtnCode getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(RtnCode rtnCode) {
		this.rtnCode = rtnCode;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}



	
	
	
}

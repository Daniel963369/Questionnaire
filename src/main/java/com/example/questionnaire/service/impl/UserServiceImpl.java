package com.example.questionnaire.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questionnaire.Repository.UserDao;
import com.example.questionnaire.constants.RtnCode;
import com.example.questionnaire.entity.Question;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.service.ifs.UserService;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.UserRes;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserDao userDao;
		
		
	@Override
	public UserRes write(List<User> userList) {
		for( User user:userList) {
			user.setDateTime(LocalDateTime.now());
		}
		userDao.saveAll(userList);
		
		return new UserRes(RtnCode.SUCCESSFUL);
	}


	@Override
	public UserRes searchUserList(int qnId) {
		if(qnId <0) {
			return new UserRes(RtnCode.QUESTION_ID_PARAM_ERROR);
		}
		List<User> userList = userDao.findAllByQnId(qnId);
		return new UserRes(RtnCode.SUCCESSFUL,userList);
	}
}	

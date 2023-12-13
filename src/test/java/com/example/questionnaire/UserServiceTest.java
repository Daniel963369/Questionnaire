package com.example.questionnaire;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.questionnaire.entity.User;
import com.example.questionnaire.service.ifs.UserService;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	@Test
	public void searchUserTest(){
		userService.searchUserList(201);
	}
	
}

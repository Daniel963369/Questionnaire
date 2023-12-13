package com.example.questionnaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionnaire.entity.User;
import com.example.questionnaire.service.ifs.UserService;
import com.example.questionnaire.vo.QuestionRes;
import com.example.questionnaire.vo.UserRes;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value = "api/user/write")
	public UserRes write(@RequestBody  UserRes res) {
		return userService.write(res.getUserList());
	}
	
	@GetMapping(value = "api/user/searchUserList")
	public UserRes searchUserList(@RequestParam int qnId) {
		System.out.println("«¢«¢«¢");
		System.out.println(qnId);
		return userService.searchUserList(qnId);
		
	}
	
}

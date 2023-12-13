package com.example.questionnaire.service.ifs;

import java.util.List;

import com.example.questionnaire.entity.User;
import com.example.questionnaire.vo.UserRes;

public interface UserService {
	
	
	public UserRes write (List<User> userList);
	public UserRes searchUserList (int qnId);
}

package com.bway.springdemo.service;

import com.bway.springdemo.model.User;

public interface UserService {
	
	void userSignup(User user);
	User userLogin(String un, String psw);



}

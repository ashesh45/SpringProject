package com.bway.springdemo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.springdemo.model.User;
import com.bway.springdemo.repository.UserRepository;
import com.bway.springdemo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
   @Autowired
	private UserRepository userRepo;
	
	@Override
	public void userSignup(User user) {
		// TODO Auto-generated method stub
		userRepo.save(user);
	}

	@Override
	public User userLogin(String un, String psw) {
		// TODO Auto-generated method stub
		return userRepo.findByUsernameAndPassword(un, psw);
	}

	@Override
	public User findByUsername(String username) {
		return userRepo.findByUsername(username).orElse(null);
	}

}

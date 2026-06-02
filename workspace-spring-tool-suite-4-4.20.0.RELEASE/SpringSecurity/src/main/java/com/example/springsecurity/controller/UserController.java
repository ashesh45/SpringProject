package com.example.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springsecurity.model.MyUser;
import com.example.springsecurity.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String getUser() {
		return "SignupForm";
	}
	
	@PostMapping("/signup")
	public String postUser(@ModelAttribute MyUser user) {
		userService.saveUser(user);
		return "redirect:/login";
	}

}

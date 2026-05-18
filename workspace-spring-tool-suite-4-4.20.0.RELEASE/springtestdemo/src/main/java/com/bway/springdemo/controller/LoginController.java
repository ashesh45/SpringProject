package com.bway.springdemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bway.springdemo.model.User;
import com.bway.springdemo.repository.UserRepository;

@Controller
public class LoginController {
	
	@Autowired
	private UserRepository userRepo;
	
	
	@GetMapping("/login")
	public String getLogin() {
		
		return "LoginForm";
	}
	
	
	@PostMapping("/login")
	public String postlogin(@ModelAttribute User u,Model model) {
		
		
		User usr = userRepo.findByUsernameAndPassword(u.getUsername(),u.getPassword());
		
		
		if(usr != null) {
			model.addAttribute("uname", u.getUsername());
		}
//			if(u.getUsername().equals("admin") && u.getPassword().equals("admin")) {
//				
//				model.addAttribute("uname", u.getUsername());
//				return "Home";
				
//			}
//			
//			model.addAttribute("msg","Invalid username or password");
//		
  return "home";
	}

}

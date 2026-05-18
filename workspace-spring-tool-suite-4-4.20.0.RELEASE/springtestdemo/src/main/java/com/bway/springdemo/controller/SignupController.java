package com.bway.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.springdemo.model.User;
import com.bway.springdemo.repository.UserRepository;

@Controller
public class SignupController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/signup")
	public String getSignup() {
		
		return "Signup";
	}

	
	@PostMapping("/signup")
	public String getsignup(@ModelAttribute User u, Model model, @RequestParam ("gender") String gender) {

////	    if (u.getFname() != null && !u.getFname().isEmpty() &&
////	        u.getLname() != null && !u.getLname().isEmpty() &&
////	        u.getUsername() != null && !u.getUsername().isEmpty() &&
////	        u.getPassword() != null && !u.getPassword().isEmpty()) {
////	    	
//////	    	model.addAttribute("user", u);
//////	    	model.addAttribute("gender", gender);
//////	    
//////            return "myhome";
//////            
////	    
////	    	
////	        model.addAttribute("msg1", "Signup successful! Please login.");
////	        return "LoginForm";
////	    } else {
////	        model.addAttribute("msg", "All fields are required");
////	        return "Signup";
//	        
	        userRepo.save(u);
	        return "LoginForm";
	    }
	}


package com.bway.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bway.springdemo.model.Doctor;
import com.bway.springdemo.repository.DoctorRepository;



@Controller
public class SignUpController {

    @Autowired
    private DoctorRepository DoctorRepo;
	
	@GetMapping("/signup")
	public String getSignup() {
		
		return "Signup";
	}

	
	@PostMapping("/signup")
	public String postsignup(@ModelAttribute Doctor d, Model model) {
//
//	    if (u.getName() != null && !u.getName().isEmpty() &&
//	        u.getEmail() != null && !u.getEmail().isEmpty() &&
//	        u.getUsername() != null && !u.getUsername().isEmpty() &&
//	        u.getPassword() != null && !u.getPassword().isEmpty()) {
//	    	
//
////            model.addAttribute("doctor",d);
////            return "doctor";
//	    	
//	        model.addAttribute("msg1", "Signup successful! Please login.");
//	        return "login";
//	    } else {
//	        model.addAttribute("msg", "All fields are required");
//	        return "signup";
//	    /}
		DoctorRepo.save(d);
		return "login";
		
		
	}
}

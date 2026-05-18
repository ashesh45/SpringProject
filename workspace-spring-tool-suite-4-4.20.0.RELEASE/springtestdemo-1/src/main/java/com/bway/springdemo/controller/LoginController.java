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
public class LoginController {
	
	@Autowired
	private DoctorRepository DoctorRepo;
	
	@GetMapping("/login")
	public String getLogin() {
		
		return "/login";
	}
	
	
	@PostMapping("/login")
	public String postlogin(@ModelAttribute Doctor d,Model model) {
		
		
		Doctor dt = DoctorRepo.findByUsernameAndPassword(d.getUsername(),d.getPassword());
		
		
		if(dt != null) {
			model.addAttribute("uname", d.getUsername());
		}
//			if(d.getUsername().equals("admin") && d.getPassword().equals("admin")) {
//				
//				model.addAttribute("uname", d.getUsername());
//				return "home";
//				
//			}
//			
//			model.addAttribute("msg","Invalid username or password");
 		
             return "home";
	}
	

}

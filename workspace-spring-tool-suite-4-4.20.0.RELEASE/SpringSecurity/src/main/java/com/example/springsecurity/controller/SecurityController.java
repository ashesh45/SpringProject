package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

	@GetMapping("/")
	public String get() {
		
		return "indexpage";
	}
	
	
	@GetMapping("/user")
	public String getuser() {
		
		return "userpage";
	}
	
	
	
	@GetMapping("/admin")
	public String getadmin() {
		
		return "adminpage";
	}
	

}

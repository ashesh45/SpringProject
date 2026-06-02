package com.example.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

	@GetMapping("/")
	public String get() {
		
		return "indexpage";
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String getuser() {
		
		return "userpage";
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String getadmin() {
		
		return "adminpage";
	}
	

	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@GetMapping("/home")
	public String gethome() {

	    return "homepage";
	}
}

package com.bway.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {
	
	@GetMapping("/profile")	
	public String getprofile(HttpSession Session) {
		 if (Session.getAttribute("activeuser") == null) {

		        return "login";
		    }

		    return "profile";
		}

}

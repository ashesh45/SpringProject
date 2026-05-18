package com.bway.springdemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.bway.springdemo.model.User;
import com.bway.springdemo.service.UserService;

import jakarta.servlet.http.HttpSession;



@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	

	
	@GetMapping("/")	
	public String getLogin() {
		
		return "login";
	}
	
	
	
	@PostMapping("/login")
	public String postlogin(@ModelAttribute User user, Model model, HttpSession session) {
		
		   User User = userService.userLogin(user.getUsername(), user.getPassword());

		    if(User != null) {
		       // model.addAttribute("user", User);
		        model.addAttribute("uname", user.getUsername());
		        session.setAttribute("activeuser", User);
		        session.setMaxInactiveInterval(500);
		        return "department"; // login success
		    } else {
		        model.addAttribute("error", "Invalid email or password");
		        return "login"; // login fail
		    }
		    
	}
	
	
	
	@GetMapping("/signup")
	public String getsignup() {
		
		return  "signup";
	}
	
	
	@PostMapping("/signup")
	public String postsignup(@ModelAttribute User user, Model model) {
		
        userService.userSignup(user);  // save to DB

        model.addAttribute("msg", "Registration successful!");
        return "login";
	}
	

	@GetMapping("/logout")
    public String logout(HttpSession session) {

        // remove session data
        session.invalidate();

        // redirect to login page
        return "login";
    }
	
	
	
	
}

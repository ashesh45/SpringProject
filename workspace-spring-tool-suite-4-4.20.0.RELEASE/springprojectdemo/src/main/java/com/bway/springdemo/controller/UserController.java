package com.bway.springdemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.bway.springdemo.model.User;
import com.bway.springdemo.repository.ProductRepository;
import com.bway.springdemo.service.UserService;

import jakarta.servlet.http.HttpSession;



@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductRepository ProductRepo;
	

	
	@GetMapping("/")	
	public String getIndex(Model model) {
		model.addAttribute("pList", ProductRepo.findAll());
		return "customerhome";
	}
	
	
	
	
	@GetMapping("/login")	
	public String getLogin() {
		
		return "login";
	}
	
	
	
	@PostMapping("/login")
	public String postlogin(@ModelAttribute User user, Model model, HttpSession session) {

	    User dbUser = userService.userLogin(user.getUsername(), user.getPassword());

	    if (dbUser != null) {

	        model.addAttribute("uname", dbUser.getUsername());
	        session.setAttribute("activeuser", dbUser);
	        session.setMaxInactiveInterval(500);

	        if ("CUSTOMER".equalsIgnoreCase(dbUser.getRole())) {
	        	model.addAttribute("pList", ProductRepo.findAll());
	            return "customerhome";
	        }

	        return "department";
	    } else {
	        model.addAttribute("error", "Invalid email or password");
	        return "login";
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

package com.bway.springdemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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

	    User dbUser = userService.findByUsername(user.getUsername());

	    if (dbUser != null) {

	        String storedPassword = dbUser.getPassword();
	        boolean isBcrypt = storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$") || storedPassword.startsWith("$2y$");

	        if (!isBcrypt) {
	            storedPassword = passwordEncoder.encode(storedPassword);
	            dbUser.setPassword(storedPassword);
	            userService.userSignup(dbUser);
	        }

	        if (passwordEncoder.matches(user.getPassword(), storedPassword)) {
	            model.addAttribute("uname", dbUser.getUsername());
	            session.setAttribute("activeuser", dbUser);
	            session.setMaxInactiveInterval(500);

	            if ("CUSTOMER".equalsIgnoreCase(dbUser.getRole())) {
	            	model.addAttribute("pList", ProductRepo.findAll());
	                return "customerhome";
	            }

	            return "department";
	        }
	    }

	    model.addAttribute("error", "Invalid email or password");
	    return "login";
	}
	
	
	
	@GetMapping("/signup")
	public String getsignup() {
		
		return  "signup";
	}
	
	
	@PostMapping("/signup")
	public String postsignup(@ModelAttribute User user, Model model) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
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

package com.example.empsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.EmployeeService;


import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
	

	
    @Autowired
    private EmployeeService empService;	
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@GetMapping("/login")
	public String getlogin() {
		
		return "login";
		
	}
	
	
	@PostMapping("/login")
	public String postlogin(@ModelAttribute Employee e, Model model, HttpSession session) {

	    Employee emp = empRepo.findByUsernameAndPassword(e.getUsername(),e.getPassword());
	          
	    if (emp != null) {
	        session.setAttribute("loggedInUser", emp);
	        return "redirect:/dashboard";
	    }

	    model.addAttribute("msg", "Invalid username or password");
	    return "login";
	}
	

}

package com.example.empsystem.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.EmployeeService;
import com.example.empsystem.utils.MailUtils;

import java.util.Random;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
	
	
    @Autowired
    private EmployeeService empService;	
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private MailUtils mailUtils;
	
	
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
	
	@GetMapping("/forgot-password")
	public String getforgotpage() {
	    return "forgot-password";
	}
	
	@PostMapping("/send-otp")
	public String forgotPassword(@RequestParam("email") String email, HttpSession session) {
   
	    System.out.println("EMAIL " +email);
	    
	    Random random = new Random();
	    int otp = random.nextInt(900000) + 100000;
		System.out.println("OTP "+otp);
		
	    // Store OTP in session (important for verification step)
	    session.setAttribute("otp", otp);
	    session.setAttribute("email", email);

	    // Send OTP via email
	    String subject = "Your OTP for Password Reset";
	    String message = "Your OTP is: " + otp + "\nThis OTP is valid for a short time.";

	    mailUtils.sendEmail(email, subject, message);
	    return "verify-otp";
	}

	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam("otp") int otp, HttpSession session, Model model) {

	    Integer sessionOtp = (Integer) session.getAttribute("otp");
	    String email = (String) session.getAttribute("email");

	    if (sessionOtp == null || email == null) {
	        model.addAttribute("msg", "Session expired. Please try again.");
	        return "forgot-password";
	    }

	    if (sessionOtp.equals(otp)) {
	        return "password-change";
	    } else {
	        model.addAttribute("msg", "Invalid OTP. Please try again.");
	        return "verify-otp";
	    }
	}

	@PostMapping("/password-change")
	public String changePassword(@RequestParam("newPassword") String newPassword,
	                             @RequestParam("confirmPassword") String confirmPassword,
	                             HttpSession session, Model model) {

	    String email = (String) session.getAttribute("email");

	    if (email == null) {
	        model.addAttribute("msg", "Session expired. Please try again.");
	        return "forgot-password";
	    }

	    if (!newPassword.equals(confirmPassword)) {
	        model.addAttribute("msg", "Passwords do not match.");
	        return "password-change";
	    }

	    Employee emp = empRepo.findByEmail(email);
	    if (emp != null) {
	        emp.setPassword(newPassword);
	        empRepo.save(emp);
	    }

	    session.invalidate();
	    return "redirect:/login";
	}
	
}

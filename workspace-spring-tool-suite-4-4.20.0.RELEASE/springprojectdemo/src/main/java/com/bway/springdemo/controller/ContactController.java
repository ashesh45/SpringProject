package com.bway.springdemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.springdemo.repository.FinanceRepository;
import com.bway.springdemo.utils.MailUtils;

import jakarta.servlet.http.HttpSession;

@Controller
public class ContactController {

    @Autowired
	private MailUtils mailutil;
    
    @Autowired
    private FinanceRepository financeRepo;

    @GetMapping("/contact")
    public String getcontact(HttpSession session) {

        if (session.getAttribute("activeuser") == null) {
            return "login";
        }

        return "contact";
    }

    @PostMapping("/contact")
    public String postcontact(
            @RequestParam("email") String ToEmail,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            Model model) {

        
            mailutil.sendEmail(ToEmail, subject, message);
            model.addAttribute("msg", "Message Sent Successfully!");    

        return "contact";
    }
    
    
    
    
	
	@GetMapping("/news")
	public String getNews(Model model, HttpSession session) {
		
        if (session.getAttribute("activeuser") == null) {
            return "redirect:/login";
        }

	    model.addAttribute("clist", financeRepo.findAll());
	    return "news";
	}
}

package com.bway.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.springdemo.model.Contact;

@Controller
public class ContactController {
	
	@GetMapping("/contact")
	public String getContatc() {
		
		return "contact";
	}

	
	
	
	@PostMapping("/contact")
	public String postContact(@ModelAttribute Contact contact, Model model) {

	      // validation
        if(contact.getName().isEmpty() ||
           contact.getEmail().isEmpty() ||
           contact.getSubject().isEmpty() ||
           contact.getMessage().isEmpty()) {

            model.addAttribute("msg1", "All fields are required!");
            return "contact";
        }
       
        
//        model.addAttribute("cname", contact.getName());
//        model.addAttribute("cemail", contact.getEmail());
//        model.addAttribute("csubject", contact.getSubject());
//        model.addAttribute("cmessage", contact.getMessage());
        
//        model.addAttribute("contact", contact);
//         return "contactinfo";
	  
	    // success message
	    model.addAttribute("msg", "Message sent successfully!");
	    
	    return "contact";
	}
}

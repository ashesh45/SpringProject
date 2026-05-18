package com.bway.springdemo.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class GalleryController {
	
	
	@GetMapping("/gallery")	
	public String getdepartment(Model model, HttpSession session) {
		
		if(session.getAttribute("activeuser") == null) {
			
			return "login";
		}
		
		String[] imgName = new File("src/main/resources/static/images/").list();
		
		model.addAttribute("imgList", imgName);
		
		
		return "gallery";
	}

}

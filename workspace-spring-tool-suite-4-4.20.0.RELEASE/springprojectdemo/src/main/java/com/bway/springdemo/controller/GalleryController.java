package com.bway.springdemo.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bway.springdemo.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class GalleryController {
	
	@Autowired
	private ProductRepository ProductRepo;
	
	@GetMapping("/gallery")	
	public String getdepartment(Model model, HttpSession session) {
		
		if(session.getAttribute("activeuser") == null) {
			
			return "login";
		}
		
		String[] imgName = new File("src/main/resources/static/images/").list();
		
		model.addAttribute("imgList", imgName);
		
		
		return "gallery";
	}

	
	
	@GetMapping("/pgallery")
	public String getProductGallery(Model model) {

	    model.addAttribute("plist", ProductRepo.findAll());
	    return "ProductGalleryForm";
	}
}

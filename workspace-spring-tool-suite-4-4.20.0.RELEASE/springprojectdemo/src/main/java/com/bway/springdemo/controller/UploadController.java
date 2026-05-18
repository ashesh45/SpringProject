package com.bway.springdemo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
public class UploadController {
	
	
	
	@GetMapping("/upload")	
	public String upload(HttpSession Session) {
		if(Session.getAttribute("activeuser") == null) {
			
			return "login";
		}
		return "uploadform";
	}
	
	  @PostMapping("/upload")
	public String postupload(@RequestParam("image") MultipartFile image, Model model) throws IOException {
		
		   if(!image.isEmpty()) {
			   
			   Files.copy(image.getInputStream(), Path.of("src/main/resources/static/images/"+ image.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
		   
			   model.addAttribute("msg","upload success");
	    	return "uploadform";
	}
		   model.addAttribute("msg","upload failed");
		   return "uploadform";
	  }

}

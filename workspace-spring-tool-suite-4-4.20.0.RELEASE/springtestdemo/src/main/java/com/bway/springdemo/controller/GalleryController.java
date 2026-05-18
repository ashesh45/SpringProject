package com.bway.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GalleryController {
	@GetMapping("/Gallery")
	public String getGallery() {
		
		return "Gallery";
	}

}

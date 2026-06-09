package com.bway.springdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GalleryController {

	@GetMapping("/Gallery")
	public String getGallery() {
		return "Gallery";
	}

}

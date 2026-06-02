package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@PostMapping("/add")
	public String addStudent() {
		
		return "add success";
	}

	
	@GetMapping("/list")
	public String addlist() {
		
		return "list success";
	}
}

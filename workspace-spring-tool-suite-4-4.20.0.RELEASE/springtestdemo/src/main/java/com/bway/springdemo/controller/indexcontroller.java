package com.bway.springdemo.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class indexcontroller {
	
	@GetMapping("/")
	public Map<String, String> getIndex() {
		return Map.of("message", "Welcome to Spring Demo API");
	}

}

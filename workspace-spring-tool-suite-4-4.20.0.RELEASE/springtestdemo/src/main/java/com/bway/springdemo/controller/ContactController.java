package com.bway.springdemo.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bway.springdemo.model.Contact;

@RestController
public class ContactController {
	
	@PostMapping("/contact")
	public ResponseEntity<?> postContact(@RequestBody Contact contact) {
		if (contact.getName().isEmpty() ||
			contact.getEmail().isEmpty() ||
			contact.getSubject().isEmpty() ||
			contact.getMessage().isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "All fields are required!"));
		}
		return ResponseEntity.ok(Map.of("message", "Message sent successfully!"));
	}

}

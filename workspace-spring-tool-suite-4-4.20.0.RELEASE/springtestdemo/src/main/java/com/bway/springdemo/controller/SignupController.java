package com.bway.springdemo.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bway.springdemo.model.User;
import com.bway.springdemo.repository.UserRepository;

@RestController
public class SignupController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public ResponseEntity<?> getsignup(@RequestBody User u) {
		if (u.getFname() == null || u.getFname().isEmpty() ||
			u.getLname() == null || u.getLname().isEmpty() ||
			u.getUsername() == null || u.getUsername().isEmpty() ||
			u.getPassword() == null || u.getPassword().isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "All fields are required"));
		}
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		userRepo.save(u);
		return ResponseEntity.ok(Map.of("message", "Signup successful! Please login."));
	}

}

package com.bway.springdemo.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bway.springdemo.dto.JwtRequest;
import com.bway.springdemo.dto.JwtResponse;
import com.bway.springdemo.security.JwtHelper;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserDetailsService userDetailsService;
	private final AuthenticationManager authenticationManager;
	private final JwtHelper jwtHelper;

	public AuthController(UserDetailsService userDetailsService, AuthenticationManager authenticationManager,
			JwtHelper jwtHelper) {
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
		this.jwtHelper = jwtHelper;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody JwtRequest request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("error", "Invalid username or password"));
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = jwtHelper.generateToken(userDetails);

		JwtResponse response = JwtResponse.builder()
				.username(userDetails.getUsername())
				.jwtToken(token)
				.build();

		return ResponseEntity.ok(response);
	}

}

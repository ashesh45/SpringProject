package com.bway.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bway.springdemo.model.Appointment;
import com.bway.springdemo.repository.AppointmentRepository;

@Controller
public class AppointmentController {
    
	@Autowired
	private AppointmentRepository appointmentRepo;
	
	@GetMapping("/appointment")
	public String getLogin() {
		
		return "/appointment";
}
	
	
	@PostMapping("/appointment")
	public String postappointment(@ModelAttribute Appointment a, Model model) {
		
		
		appointmentRepo.save(a);
		return "login";
		
		
	}
	}

package com.bway.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.bway.springdemo.model.Patient;
import com.bway.springdemo.repository.PatientRepository;

@Controller
public class PatientController {
	
	@Autowired
	private PatientRepository PatientRepo;
	
	
	@GetMapping("/patient")
	public String getPatient() {
		
		return "patient";

}
	
	@PostMapping("/patient")
	 public String postpatient(@ModelAttribute Patient p, Model model) {
		
		PatientRepo.save(p);
		
		  model.addAttribute("patient",p);
          return "patientlist";
           
//		model.addAttribute("msg","patient register success");
//		return "patient";
		
	}
}
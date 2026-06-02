package com.example.empsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.empsystem.service.LeaveService;

@Controller
@RequestMapping("/admin")
public class AdminLeaveController {

	@Autowired
	private LeaveService leaveService;

	@GetMapping("/approve/{id}")
	public String approveLeave(@PathVariable Long id) {
		leaveService.approveLeave(id);
		return "redirect:/employee/all-leaves";
	}

	@GetMapping("/reject/{id}")
	public String rejectLeave(@PathVariable Long id) {
		leaveService.rejectLeave(id);
		return "redirect:/employee/all-leaves";
	}

}

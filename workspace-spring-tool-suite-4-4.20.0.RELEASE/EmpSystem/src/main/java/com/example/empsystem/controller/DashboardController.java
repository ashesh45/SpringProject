package com.example.empsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.empsystem.enumm.LeaveStatus;
import com.example.empsystem.repository.DepartmentRepository;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.repository.LeaveRequestRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/employee")
public class DashboardController {

	@Autowired
	private EmployeeRepository empRepo;

	@Autowired
	private DepartmentRepository deptRepo;

	@Autowired
	private LeaveRequestRepository leaveRepo;
 

	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpSession session) {
	if(session.getAttribute("loggedInUser") == null) {
			
			return "login";
		}
		model.addAttribute("empCount", empRepo.count());
		model.addAttribute("deptCount", deptRepo.count());
		model.addAttribute("leaveCount", leaveRepo.count());
		model.addAttribute("pendingCount", leaveRepo.findByStatus(LeaveStatus.PENDING).size());
		return "dashboard.html";
	}
	

	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@GetMapping("/profile")	
	public String getprofile(HttpSession Session) {
		 if (Session.getAttribute("loggedInUser") == null) {

		        return "login";
		    }

		    return "profile";
		}


}

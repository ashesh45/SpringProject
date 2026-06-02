package com.example.empsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.empsystem.model.Employee;
import com.example.empsystem.model.LeaveRequest;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.LeaveService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/employee")
public class LeaveRequestController {
	
	@Autowired
    private LeaveService leaveService;
	
	@Autowired
	private EmployeeRepository empRepo;

	
	
	@GetMapping("/leave-request")
	public String showLeaveForm(Model model) {
		model.addAttribute("leaveRequest", new LeaveRequest());
		return "leaverequest";
	}

	@PostMapping("/leave-request")
	public String applyLeave(@ModelAttribute LeaveRequest request, HttpSession session, Model model) {
		Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/login";
		}
		Employee emp = empRepo.findByUsername(loggedInUser.getUsername());
		if (emp == null) {
			model.addAttribute("error", "No employee record found for your account");
			return "leaverequest";
		}
		leaveService.applyLeave(emp.getId(), request);
		model.addAttribute("msg", "Leave applied successfully");
		return "leaverequest";
	}

	
	  @GetMapping("/all-leaves")
	    public String getAllLeaves(Model model) {

	        model.addAttribute("leaves", leaveService.getAllLeaves());

	        return "leavelist";
	    }
	  
	  
	
	  
	  @GetMapping("/myleaves")
	  public String myLeaves(HttpSession session, Model model) {

	      Employee emp = (Employee) session.getAttribute("loggedInUser");

	      List<LeaveRequest> leaves =
	              leaveService.findByEmployee(emp.getId());

	      model.addAttribute("leaves", leaves);

	      return "myleaves";
	  }
}

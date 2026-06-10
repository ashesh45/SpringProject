package com.example.empsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.empsystem.model.Attendance;
import com.example.empsystem.model.Employee;
import com.example.empsystem.service.AttendanceService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/employee")
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@GetMapping
	public String showAttendancePage(HttpSession session, Model model) {
		Employee emp = (Employee) session.getAttribute("loggedInUser");
		if (emp == null) {
			return "redirect:/login";
		}

		Attendance todayAttendance = attendanceService.getTodayAttendance(emp.getId());
		List<Attendance> history = attendanceService.getAttendanceByEmployee(emp.getId());

		boolean canCheckIn = todayAttendance == null;
		boolean canCheckOut = todayAttendance != null && todayAttendance.getCheckOutTime() == null;

		model.addAttribute("today", todayAttendance);
		model.addAttribute("history", history);
		model.addAttribute("canCheckIn", canCheckIn);
		model.addAttribute("canCheckOut", canCheckOut);

		return "attendance";
	}

	@PostMapping("/checkin")
	public String checkIn(HttpSession session, HttpServletRequest request, RedirectAttributes ra) {
		Employee emp = (Employee) session.getAttribute("loggedInUser");
		if (emp == null) {
			return "redirect:/login";
		}
		String msg = attendanceService.checkIn(emp.getId());
		ra.addFlashAttribute("msg", msg);
		String referer = request.getHeader("Referer");
		return "redirect:/attendance";
	}

	@PostMapping("/checkout")
	public String checkOut(HttpSession session, HttpServletRequest request, RedirectAttributes ra) {
		Employee emp = (Employee) session.getAttribute("loggedInUser");
		if (emp == null) {
			return "redirect:/login";
		}
		String msg = attendanceService.checkOut(emp.getId());
		ra.addFlashAttribute("msg", msg);
		String referer = request.getHeader("Referer");
		return "redirect:/attendance";
	}

}

package com.example.empsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.empsystem.model.Attendance;
import com.example.empsystem.serviceImpl.AttendanceServiceImpl;


@RestController
@RequestMapping("/attendance")
public class AttendanceController {

	   @Autowired
	    private AttendanceServiceImpl attendanceService;

	    @GetMapping("/checkin/{id}")
	    public String checkIn(@PathVariable Long id) {
	        return attendanceService.checkIn(id);
	    }

	    @GetMapping("/checkout/{id}")
	    public String checkOut(@PathVariable Long id) {
	        return attendanceService.checkOut(id);
	    }

	    @GetMapping
	    public List<Attendance> getAll() {
	        return attendanceService.getAllAttendance();
	    }
	
}

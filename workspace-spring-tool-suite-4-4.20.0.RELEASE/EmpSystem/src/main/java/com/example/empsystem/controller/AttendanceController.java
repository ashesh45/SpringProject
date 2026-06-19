package com.example.empsystem.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.empsystem.dto.AttendanceDto;
import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.AttendanceService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeRepository empRepo;

    private Employee getCurrentEmployee(Principal principal) {
        return empRepo.findByUsername(principal.getName());
    }

    @GetMapping("/today")
    public ResponseEntity<?> getMyAttendance(Principal principal) {
        Employee emp = getCurrentEmployee(principal);
        if (emp == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Employee not found");
        }

        AttendanceDto today = attendanceService.getTodayAttendance(emp.getId());
        List<AttendanceDto> history = attendanceService.getMyAttendance(emp.getId());

        boolean canCheckIn = today == null;
        boolean canCheckOut = today != null && today.getCheckOutTime() == null;

        Map<String, Object> response = new HashMap<>();
        response.put("today", today);
        response.put("history", history);
        response.put("canCheckIn", canCheckIn);
        response.put("canCheckOut", canCheckOut);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkin")
    public ResponseEntity<String> checkIn(Principal principal) {
        Employee emp = getCurrentEmployee(principal);
        if (emp == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Employee not found");
        }
        return ResponseEntity.ok(attendanceService.checkIn(emp.getId()));
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkOut(Principal principal) {
        Employee emp = getCurrentEmployee(principal);
        if (emp == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Employee not found");
        }
        return ResponseEntity.ok(attendanceService.checkOut(emp.getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AttendanceDto>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendanceSorted());
    }

    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) {
        attendanceService.exportExcel(response);
    }
}

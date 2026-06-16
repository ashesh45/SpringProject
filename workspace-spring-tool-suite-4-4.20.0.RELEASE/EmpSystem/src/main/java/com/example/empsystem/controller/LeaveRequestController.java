package com.example.empsystem.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.empsystem.dto.LeaveRequestDto;
import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.LeaveService;

@RestController
@RequestMapping("/api/leaves")
public class LeaveRequestController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private EmployeeRepository empRepo;

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @PostMapping
    public ResponseEntity<?> applyLeave(@RequestBody LeaveRequestDto dto,
                                        Principal principal) {

        Employee emp = empRepo.findByUsername(principal.getName());
        if (emp == null) {
            return ResponseEntity.badRequest().body("Employee record not found");
        }
        LeaveRequestDto result = leaveService.applyLeave(emp.getId(), dto);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/my")
    public ResponseEntity<List<LeaveRequestDto>> myLeaves(Principal principal) {

        Employee emp = empRepo.findByUsername(principal.getName());
        if (emp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(leaveService.getLeavesByEmployee(emp.getId()));
    }
}

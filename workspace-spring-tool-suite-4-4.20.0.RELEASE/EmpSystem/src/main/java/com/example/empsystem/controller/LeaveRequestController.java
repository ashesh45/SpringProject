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

import com.example.empsystem.model.Employee;
import com.example.empsystem.model.LeaveRequest;
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
    public ResponseEntity<?> applyLeave(@RequestBody LeaveRequest request,
                                        Principal principal) {

        Employee emp = empRepo.findByUsername(principal.getName());
        if (emp == null) {
            return ResponseEntity.badRequest().body("Employee record not found");
        }
        leaveService.applyLeave(emp.getId(), request);
        return ResponseEntity.ok("Leave applied successfully");
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/my")
    public ResponseEntity<List<LeaveRequest>> myLeaves(Principal principal) {

        Employee emp = empRepo.findByUsername(principal.getName());
        if (emp == null) {
            return ResponseEntity.notFound().build();
        }
        List<LeaveRequest> leaves = leaveService.findByEmployee(emp.getId());
        return ResponseEntity.ok(leaves);
    }
}

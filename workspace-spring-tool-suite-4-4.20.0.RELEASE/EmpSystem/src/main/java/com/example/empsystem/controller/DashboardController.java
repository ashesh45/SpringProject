package com.example.empsystem.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.empsystem.enumm.LeaveStatus;
import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.DepartmentRepository;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.repository.LeaveRequestRepository;

@RestController
public class DashboardController {

    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private DepartmentRepository deptRepo;

    @Autowired
    private LeaveRequestRepository leaveRepo;

    @GetMapping("/api/dashboard")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<Map<String, Object>> dashboard() {

        Map<String, Object> response = new HashMap<>();
        response.put("empCount", empRepo.count());
        response.put("deptCount", deptRepo.count());
        response.put("leaveCount", leaveRepo.count());
        response.put("pendingCount", leaveRepo.findByStatus(LeaveStatus.PENDING).size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/profile")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<Employee> getProfile(Principal principal) {
        Employee employee = empRepo.findByUsername(principal.getName());
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }
}

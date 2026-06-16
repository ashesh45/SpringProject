package com.example.empsystem.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.empsystem.dto.PayrollDto;
import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.PayrollService;

@RestController
public class MyPayrollController {

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private EmployeeRepository empRepo;

    @GetMapping("/api/payroll/my")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<List<PayrollDto>> myPayroll(Principal principal) {
        Employee emp = empRepo.findByUsername(principal.getName());
        if (emp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payrollService.getPayrollsByEmployeeId(emp.getId()));
    }
}

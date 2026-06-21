package com.example.empsystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.empsystem.dto.PayrollDto;
import com.example.empsystem.dto.request.CreatePayrollRequest;
import com.example.empsystem.model.Payroll;
import com.example.empsystem.repository.PayrollRepository;
import com.example.empsystem.service.PayrollService;
import com.example.empsystem.utils.AllPayrollpdfView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/payroll")
public class AdminPayrollController {

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private PayrollRepository payrollRepo;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPayroll(@RequestBody CreatePayrollRequest request) {
        PayrollDto saved = payrollService.createPayroll(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PayrollDto>> getAllPayrolls() {
        return ResponseEntity.ok(payrollService.getAllPayrolls());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/export/pdf")
    public void exportPdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Payroll> list = payrollRepo.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("list", list);
        new AllPayrollpdfView().render(model, request, response);
    }
}

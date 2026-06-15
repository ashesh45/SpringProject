package com.example.empsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.empsystem.model.LeaveRequest;
import com.example.empsystem.service.LeaveService;

@RestController
@RequestMapping("/admin/leaves")
public class AdminLeaveController {

    @Autowired
    private LeaveService leaveService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveLeave(@PathVariable Long id) {
        leaveService.approveLeave(id);
        return ResponseEntity.ok("Leave approved successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/reject/{id}")
    public ResponseEntity<String> rejectLeave(@PathVariable Long id) {
        leaveService.rejectLeave(id);
        return ResponseEntity.ok("Leave rejected successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaves() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }
}
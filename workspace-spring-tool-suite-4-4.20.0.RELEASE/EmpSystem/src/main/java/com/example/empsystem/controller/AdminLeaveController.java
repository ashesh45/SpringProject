package com.example.empsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.empsystem.dto.LeaveRequestDto;
import com.example.empsystem.service.LeaveService;

@RestController
@RequestMapping("/admin/leaves")
public class AdminLeaveController {

    @Autowired
    private LeaveService leaveService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approve/{id}")
    public ResponseEntity<LeaveRequestDto> approveLeave(@PathVariable Long id) {
        return ResponseEntity.ok(leaveService.approveLeave(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/reject/{id}")
    public ResponseEntity<LeaveRequestDto> rejectLeave(@PathVariable Long id) {
        return ResponseEntity.ok(leaveService.rejectLeave(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<LeaveRequestDto>> getAllLeaves() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }
}

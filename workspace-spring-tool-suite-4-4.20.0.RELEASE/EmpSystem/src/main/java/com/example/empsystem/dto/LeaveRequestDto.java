package com.example.empsystem.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequestDto {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType;
    private String status;
    private String reason;
    private LocalDate appliedDate;

    // Employee reference
    private Long employeeId;
    private String employeeName;
}

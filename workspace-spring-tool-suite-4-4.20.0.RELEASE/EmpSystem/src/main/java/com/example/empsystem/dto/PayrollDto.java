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
public class PayrollDto {

    private Long id;
    private double basicSalary;
    private double overtimeHours;
    private double overtimeRate;
    private double bonus;
    private double deduction;
    private double netSalary;
    private LocalDate salaryMonth;

    // Employee reference
    private Long employeeId;
    private String employeeName;
}

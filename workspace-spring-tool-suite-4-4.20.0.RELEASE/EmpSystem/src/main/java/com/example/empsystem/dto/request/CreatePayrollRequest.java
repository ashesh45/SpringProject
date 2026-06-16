package com.example.empsystem.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePayrollRequest {

    private Long employeeId;
    private double basicSalary;
    private double overtimeHours;
    private double overtimeRate;
    private double bonus;
    private double deduction;
    private LocalDate salaryMonth;
}

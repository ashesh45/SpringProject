package com.example.empsystem.dto.mapper;

import com.example.empsystem.dto.PayrollDto;
import com.example.empsystem.dto.request.CreatePayrollRequest;
import com.example.empsystem.model.Payroll;

public class PayrollMapper {

    private PayrollMapper() {}

    public static PayrollDto toDto(Payroll entity) {
        if (entity == null) return null;

        return PayrollDto.builder()
                .id(entity.getId())
                .basicSalary(entity.getBasicSalary())
                .overtimeHours(entity.getOvertimeHours())
                .overtimeRate(entity.getOvertimeRate())
                .bonus(entity.getBonus())
                .deduction(entity.getDeduction())
                .netSalary(entity.getNetSalary())
                .salaryMonth(entity.getSalaryMonth())
                .employeeId(entity.getEmployee() != null ? entity.getEmployee().getId() : null)
                .employeeName(entity.getEmployee() != null
                        ? entity.getEmployee().getFname() + " " + entity.getEmployee().getLname()
                        : null)
                .build();
    }

    public static Payroll toEntity(CreatePayrollRequest request) {
        if (request == null) return null;

        Payroll entity = new Payroll();
        entity.setBasicSalary(request.getBasicSalary());
        entity.setOvertimeHours(request.getOvertimeHours());
        entity.setOvertimeRate(request.getOvertimeRate());
        entity.setBonus(request.getBonus());
        entity.setDeduction(request.getDeduction());
        entity.setSalaryMonth(request.getSalaryMonth());
        return entity;
    }
}

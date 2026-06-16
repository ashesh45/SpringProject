package com.example.empsystem.service;

import java.util.List;

import com.example.empsystem.dto.PayrollDto;
import com.example.empsystem.dto.request.CreatePayrollRequest;

public interface PayrollService {

    PayrollDto createPayroll(CreatePayrollRequest request);

    List<PayrollDto> getAllPayrolls();

    List<PayrollDto> getPayrollsByEmployeeId(Long employeeId);
}

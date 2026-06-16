package com.example.empsystem.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.empsystem.dto.PayrollDto;
import com.example.empsystem.dto.mapper.PayrollMapper;
import com.example.empsystem.dto.request.CreatePayrollRequest;
import com.example.empsystem.model.Employee;
import com.example.empsystem.model.Payroll;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.repository.PayrollRepository;
import com.example.empsystem.service.PayrollService;

@Service
@Transactional
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private PayrollRepository payrollRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public PayrollDto createPayroll(CreatePayrollRequest request) {
        Employee employee = employeeRepo.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + request.getEmployeeId()));

        Payroll entity = PayrollMapper.toEntity(request);
        entity.setEmployee(employee);

        // Calculate net salary
        double overtimePay = entity.getOvertimeHours() * entity.getOvertimeRate();
        double grossSalary = entity.getBasicSalary() + overtimePay + entity.getBonus();
        double netSalary = grossSalary - entity.getDeduction();
        entity.setNetSalary(netSalary);

        entity = payrollRepo.save(entity);
        return PayrollMapper.toDto(entity);
    }

    @Override
    public List<PayrollDto> getAllPayrolls() {
        return payrollRepo.findAll().stream()
                .map(PayrollMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PayrollDto> getPayrollsByEmployeeId(Long employeeId) {
        return payrollRepo.findByEmployeeId(employeeId).stream()
                .map(PayrollMapper::toDto)
                .collect(Collectors.toList());
    }
}

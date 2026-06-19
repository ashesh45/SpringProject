package com.example.empsystem.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.empsystem.dto.PayrollDto;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PayrollDto createPayroll(CreatePayrollRequest request) {
        Employee employee = employeeRepo.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + request.getEmployeeId()));

        Payroll entity = modelMapper.map(request, Payroll.class);
        entity.setEmployee(employee);

        double overtimePay = entity.getOvertimeHours() * entity.getOvertimeRate();
        double grossSalary = entity.getBasicSalary() + overtimePay + entity.getBonus();
        double netSalary = grossSalary - entity.getDeduction();
        entity.setNetSalary(netSalary);

        entity = payrollRepo.save(entity);
        return modelMapper.map(entity, PayrollDto.class);
    }

    @Override
    public List<PayrollDto> getAllPayrolls() {
        return payrollRepo.findAll().stream()
                .map(entity -> modelMapper.map(entity, PayrollDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PayrollDto> getPayrollsByEmployeeId(Long employeeId) {
        return payrollRepo.findByEmployeeId(employeeId).stream()
                .map(entity -> modelMapper.map(entity, PayrollDto.class))
                .collect(Collectors.toList());
    }
}

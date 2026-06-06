package com.example.empsystem.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.empsystem.model.Payroll;
import com.example.empsystem.repository.PayrollRepository;
import com.example.empsystem.service.PayrollService;

@Service
public class PayrollServiceImpl implements PayrollService {

	@Autowired
	private PayrollRepository payrollRepository;
	
	@Override
	public Payroll calculateAndSave(Payroll payroll) {
		// TODO Auto-generated method stub
        double overtimePay =
                payroll.getOvertimeHours() * payroll.getOvertimeRate();

        double grossSalary =
                payroll.getBasicSalary() + overtimePay + payroll.getBonus();

        double netSalary = grossSalary - payroll.getDeduction();

        payroll.setNetSalary(netSalary);

        return payrollRepository.save(payroll);
	}

	@Override
	public List<Payroll> getAll() {
		// TODO Auto-generated method stub
		return payrollRepository.findAll();
	}

	@Override
	public Payroll getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payroll> getByEmployeeId(Long employeeId) {
		// TODO Auto-generated method stub
		return payrollRepository.findByEmployeeId(employeeId);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

}

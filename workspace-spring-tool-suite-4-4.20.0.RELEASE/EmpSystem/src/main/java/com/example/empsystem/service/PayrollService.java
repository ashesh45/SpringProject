package com.example.empsystem.service;

import java.util.List;

import com.example.empsystem.model.Payroll;

public interface PayrollService {


    Payroll calculateAndSave(Payroll payroll);

    List<Payroll> getAll();

    Payroll getById(Long id);

    List<Payroll> getByEmployeeId(Long employeeId);

    void deleteById(Long id);

	
}

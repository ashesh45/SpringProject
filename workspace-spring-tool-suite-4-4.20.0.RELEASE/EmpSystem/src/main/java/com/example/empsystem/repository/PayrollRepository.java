package com.example.empsystem.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.empsystem.model.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
	

    List<Payroll> findByEmployeeId(Long employeeId);

    List<Payroll> findBySalaryMonth(LocalDate salaryMonth);

}

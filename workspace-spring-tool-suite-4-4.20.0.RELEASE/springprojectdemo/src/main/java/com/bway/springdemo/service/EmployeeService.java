package com.bway.springdemo.service;

import java.util.List;

import com.bway.springdemo.model.Employee;

public interface EmployeeService {
	
    void addEmp(Employee emp);
    void deleteEmp(int id);
    void updateEmp(Employee emp);
    Employee getEmpById(Long id);
    List<Employee> getAllEmp();
    List<Employee> searcheEmp(String name);

}

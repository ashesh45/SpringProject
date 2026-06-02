package com.example.empsystem.service;

import java.util.List;

import com.example.empsystem.model.Employee;



public interface EmployeeService {
	
    void addEmp(Employee emp);
    void deleteEmp(int id);
    void updateEmp(Employee emp);
    Employee getEmpById(Long id);
    List<Employee> getAllEmp();
    List<Employee> searcheEmp(String fname);

    Employee userLogin(String username, String password);
}

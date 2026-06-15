package com.example.empsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.empsystem.model.Employee;

public interface EmployeeService {

    void addEmp(Employee emp);
    void deleteEmp(int id);
    void updateEmp(Employee emp);
    Employee getEmpById(Long id);
    List<Employee> getAllEmp();
    Page<Employee> getAllEmp(Pageable pageable);
    List<Employee> searcheEmp(String fname);

    Employee userLogin(String username, String password);
}

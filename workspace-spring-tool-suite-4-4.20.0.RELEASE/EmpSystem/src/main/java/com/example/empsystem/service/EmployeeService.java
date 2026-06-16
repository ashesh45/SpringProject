package com.example.empsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.empsystem.dto.EmployeeDTO;
import com.example.empsystem.dto.request.CreateEmployeeRequest;

public interface EmployeeService {

    EmployeeDTO createEmployee(CreateEmployeeRequest request);

    EmployeeDTO getEmployeeById(Long id);

    Page<EmployeeDTO> getAllEmployees(Pageable pageable);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO dto);

    void deleteEmployee(int id);
}

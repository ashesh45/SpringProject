package com.example.empsystem.service;

import java.util.List;

import com.example.empsystem.dto.DepartmentDto;

public interface DepartmentService {

    DepartmentDto createDepartment(DepartmentDto dto);

    DepartmentDto getDepartmentById(int id);

    List<DepartmentDto> getAllDepartments();

    DepartmentDto updateDepartment(int id, DepartmentDto dto);

    void deleteDepartment(int id);
}

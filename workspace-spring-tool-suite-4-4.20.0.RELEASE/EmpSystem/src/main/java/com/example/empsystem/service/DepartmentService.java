package com.example.empsystem.service;

import java.util.List;
import com.example.empsystem.model.Department;

public interface DepartmentService {

    void addDept(Department dept);

    void deleteDept(int id);

    void updateDept(Department dept);

    Department getDeptById(int id);

    List<Department> getAllDept();

    List<Department> searchDept(String name);
}

package com.bway.springdemo.service;

import java.util.List;



import com.bway.springdemo.model.Department;


public interface DepartmentService {
	

    void addDept(Department dept);
    void deleteDept(int id);
    void updateDept(Department dept);
    Department getDeptById(int id);
    List<Department> getAllDept();
    List<Department> seachDept(String name);
    

    
}

package com.bway.springdemo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.springdemo.model.Department;
import com.bway.springdemo.repository.DepartmentRepository;
import com.bway.springdemo.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepo;
	
  

	@Override
	public void addDept(Department dept) {
		// TODO Auto-generated method stub
		departmentRepo.save(dept);
	}


	@Override
	public void deleteDept(int id) {
		// TODO Auto-generated method stub
		   departmentRepo.deleteById(id);
	}


	@Override
	public void updateDept(Department dept) {
		// TODO Auto-generated method stub
		departmentRepo.save(dept);
	}
	

	@Override
	public Department getDeptById(int id) {
	    return departmentRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
	}


	@Override
	public List<Department> getAllDept() {
		// TODO Auto-generated method stub
		return departmentRepo.findAll();
	}


	@Override
	public List<Department> seachDept(String name) {
	
		return departmentRepo.findByNameContaining(name);
	}
	  



}

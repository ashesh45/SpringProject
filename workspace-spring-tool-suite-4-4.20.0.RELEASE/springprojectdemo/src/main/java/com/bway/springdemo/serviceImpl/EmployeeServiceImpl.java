package com.bway.springdemo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.springdemo.model.Employee;
import com.bway.springdemo.repository.EmployeeRepository;
import com.bway.springdemo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	
	@Autowired
	private  EmployeeRepository EmpRepo;

	@Override
	public void addEmp(Employee emp) {
		  EmpRepo.save(emp);
		
	}

	@Override
	public void deleteEmp(int id) {
		 EmpRepo.deleteById((long) id);
		
	}

	@Override
	public void updateEmp(Employee emp) {
		// TODO Auto-generated method stub
		EmpRepo.save(emp);
	}

	@Override
	public Employee getEmpById(Long id) {
		  return EmpRepo.findById((long) id)
		            .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
	}

	@Override
	public List<Employee> getAllEmp() {
		// TODO Auto-generated method stub
		return EmpRepo.findAll();
	}

	@Override
	public List<Employee> searcheEmp(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

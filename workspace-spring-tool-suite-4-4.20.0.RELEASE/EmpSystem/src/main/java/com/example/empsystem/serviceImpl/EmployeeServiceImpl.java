package com.example.empsystem.serviceImpl;


import com.example.empsystem.controller.AuthController;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.EmployeeService;



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
	public List<Employee> searcheEmp(String fname) {
		// TODO Auto-generated method stub
		 return EmpRepo.findByFnameContainingIgnoreCase(fname);
	}

	@Override
	public Employee userLogin(String username, String password) {
		// TODO Auto-generated method stub
		return EmpRepo.findByUsernameAndPassword(username, password);
	}
}


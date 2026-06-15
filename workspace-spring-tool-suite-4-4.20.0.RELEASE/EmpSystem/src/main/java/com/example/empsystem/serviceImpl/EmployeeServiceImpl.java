package com.example.empsystem.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository EmpRepo;

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
		EmpRepo.save(emp);
	}

	@Override
	public Employee getEmpById(Long id) {
		return EmpRepo.findById((long) id)
				.orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
	}

	@Override
	public List<Employee> getAllEmp() {
		return EmpRepo.findAll();
	}

	@Override
	public Page<Employee> getAllEmp(Pageable pageable) {
		return EmpRepo.findAll(pageable);
	}

	@Override
	public List<Employee> searcheEmp(String fname) {
		return EmpRepo.findByFnameContainingIgnoreCase(fname);
	}

	@Override
	public Employee userLogin(String username, String password) {
		return EmpRepo.findByUsernameAndPassword(username, password);
	}
}


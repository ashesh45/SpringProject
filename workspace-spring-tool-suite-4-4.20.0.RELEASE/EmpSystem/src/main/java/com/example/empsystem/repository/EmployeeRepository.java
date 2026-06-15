package com.example.empsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.empsystem.model.Employee;



public interface EmployeeRepository extends JpaRepository <Employee, Long> {

	List<Employee> findByFnameContainingIgnoreCase(String fname);
	
	Employee findByUsernameAndPassword(String username, String password);

	Employee findByUsername(String username);

	Employee findByEmail(String email);

	List<Employee> findByFnameContainingIgnoreCaseOrLnameContainingIgnoreCase(String fname, String lname);


}

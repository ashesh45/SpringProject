package com.example.empsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.empsystem.model.Department;
import com.example.empsystem.model.Employee;

public interface DepartmentRepository extends JpaRepository <Department, Integer>  {

	

}

package com.bway.springdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bway.springdemo.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository <Department, Integer> {
	
	
	List<Department> findByNameContaining(String name);

}

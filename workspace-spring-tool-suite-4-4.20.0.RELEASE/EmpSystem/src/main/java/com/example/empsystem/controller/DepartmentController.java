package com.example.empsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.empsystem.model.Department;
import com.example.empsystem.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Add Department
    @PostMapping
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addDepartment(@RequestBody Department department) {

        departmentService.addDept(department);

        return ResponseEntity.status(HttpStatus.CREATED).body("Department added successfully");
                
    }

    // Get All Departments
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<List<Department>> getAllDepartments() {

        List<Department> departments = departmentService.getAllDept();

        return ResponseEntity.ok(departments);
    }

    // Get Department By Id
    @GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int id) {

        Department department = departmentService.getDeptById(id);

        if (department == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(department);
    }

    // Update Department
    @PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateDepartment(
            @PathVariable int id,
            @RequestBody Department department) {

        Department existing = departmentService.getDeptById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setDeptName(department.getDeptName());
        existing.setHod(department.getHod());
        existing.setPhone(department.getPhone());

        departmentService.updateDept(existing);

        return ResponseEntity.ok("Department updated successfully");
    }

    // Delete Department
	@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable int id) {

        Department existing = departmentService.getDeptById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        departmentService.deleteDept(id);

        return ResponseEntity.ok("Department deleted successfully");
    }
}

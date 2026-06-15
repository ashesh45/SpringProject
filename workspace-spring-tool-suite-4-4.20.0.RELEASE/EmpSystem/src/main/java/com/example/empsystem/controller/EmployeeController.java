package com.example.empsystem.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.empsystem.model.Employee;
import com.example.empsystem.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService empService;

	private final Path uploadDir = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "images");

	@PostMapping
	public ResponseEntity<?> createEmployee(
			@RequestPart("employee") Employee emp,
			@RequestParam("file") MultipartFile file) {

		try {
			if (!file.isEmpty()) {
				Files.createDirectories(uploadDir);

				String sanitizedName = emp.getFname()
						.toLowerCase()
						.replaceAll("[^a-z0-9]", "_");
				String fileName = sanitizedName + ".jpg";

				Path uploadPath = uploadDir.resolve(fileName);
				Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
				emp.setPhoto(fileName);
			}

			empService.addEmp(emp);
			return ResponseEntity.status(HttpStatus.CREATED).body(emp);

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("File upload failed: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<Page<Employee>> getAllEmployees(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Employee> empPage = empService.getAllEmp(pageable);
		return ResponseEntity.ok(empPage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		try {
			Employee emp = empService.getEmpById(id);
			return ResponseEntity.ok(emp);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateEmployee(
			@PathVariable Long id,
			@RequestPart("employee") Employee employee,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		try {
			Employee existing = empService.getEmpById(id);
			if (existing == null) {
				return ResponseEntity.notFound().build();
			}

			existing.setFname(employee.getFname());
			existing.setLname(employee.getLname());
			existing.setEmail(employee.getEmail());
			existing.setPhone(employee.getPhone());
			existing.setGender(employee.getGender());
			existing.setDob(employee.getDob());
			existing.setCompany(employee.getCompany());
			existing.setPost(employee.getPost());
			existing.setSalary(employee.getSalary());
			existing.setJoinDate(employee.getJoinDate());
			existing.setAddress(employee.getAddress());
			existing.setDepartments(employee.getDepartments());

			if (file != null && !file.isEmpty()) {
				Files.createDirectories(uploadDir);

				String sanitizedName = existing.getFname()
						.toLowerCase()
						.replaceAll("[^a-z0-9]", "_");
				String fileName = sanitizedName + ".jpg";

				Path uploadPath = uploadDir.resolve(fileName);
				Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
				existing.setPhoto(fileName);
			}

			empService.updateEmp(existing);
			return ResponseEntity.ok(existing);

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("File upload failed: " + e.getMessage());
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
		try {
			empService.deleteEmp(id);
			return ResponseEntity.ok("Employee deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting employee: " + e.getMessage());
		}
	}
}

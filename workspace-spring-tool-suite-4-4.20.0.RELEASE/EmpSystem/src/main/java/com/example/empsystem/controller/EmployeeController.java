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

import com.example.empsystem.dto.EmployeeDTO;
import com.example.empsystem.dto.request.CreateEmployeeRequest;
import com.example.empsystem.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService empService;

    private final Path uploadDir = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "images");

    @PostMapping
    public ResponseEntity<?> createEmployee(
            @RequestPart("employee") CreateEmployeeRequest request,
            @RequestParam("file") MultipartFile file) {

        try {
            if (!file.isEmpty()) {
                Files.createDirectories(uploadDir);

                String sanitizedName = request.getFname()
                        .toLowerCase()
                        .replaceAll("[^a-z0-9]", "_");
                String fileName = sanitizedName + ".jpg";

                Path uploadPath = uploadDir.resolve(fileName);
                Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
                request.setPhoto(fileName);
            }

            EmployeeDTO result = empService.createEmployee(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(empService.getAllEmployees(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(empService.getEmployeeById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @RequestPart("employee") EmployeeDTO dto,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            if (file != null && !file.isEmpty()) {
                Files.createDirectories(uploadDir);

                String sanitizedName = dto.getFname()
                        .toLowerCase()
                        .replaceAll("[^a-z0-9]", "_");
                String fileName = sanitizedName + ".jpg";

                Path uploadPath = uploadDir.resolve(fileName);
                Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
                dto.setPhoto(fileName);
            }

            EmployeeDTO result = empService.updateEmployee(id, dto);
            return ResponseEntity.ok(result);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        try {
            empService.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting employee: " + e.getMessage());
        }
    }
}

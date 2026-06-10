package com.example.empsystem.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.empsystem.model.Department;
import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.DepartmentRepository;
import com.example.empsystem.service.DepartmentService;
import com.example.empsystem.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class EmployeeController {

	@Autowired
	private EmployeeService EmpService;

	@Autowired
	private DepartmentService deptService;

	private final Path uploadDir = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "images");

	@GetMapping("/addemp")
	public String openAddEmpPage(Model model) {
//		if (session.getAttribute("loggedInUser") == null) {
//			return "login";
//		}
		model.addAttribute("employee", new Employee());
		model.addAttribute("dlist", deptService.getAllDept());
		return "addemp";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addemp")
	public String addemp(@ModelAttribute Employee emp,
	                     @RequestParam("file") MultipartFile file,
	                     Model model,
	                     HttpSession session) {

		if (session.getAttribute("loggedInUser") == null) {
			return "login";
		}

		try {
			if (!file.isEmpty()) {
				Files.createDirectories(uploadDir);

				String sanitizedName = emp.getFname()
						.toLowerCase()
						.replaceAll("[^a-z0-9]", "_");
				String fileName = sanitizedName + ".jpg";

				Path uploadPath = uploadDir.resolve(fileName);
				System.out.println("Saving file to: " + uploadPath.toAbsolutePath());
				Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
				emp.setPhoto(fileName);
			}

			EmpService.addEmp(emp);
			model.addAttribute("msg", "Employee added successfully");
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("msg", "File upload failed: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "Error: " + e.getMessage());
		}

		model.addAttribute("employee", new Employee());
		model.addAttribute("dlist", deptService.getAllDept());
		return "addemp";
	}

	@GetMapping("/emplist")
	public String getallemp(Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "login";
		}
		model.addAttribute("emplist", EmpService.getAllEmp());
		return "emplist";
	}

	@GetMapping("/editemp/{id}")
	public String editEmp(@PathVariable Long id, Model model, HttpSession session) {
		if (session.getAttribute("loggedInUser") == null) {
			return "login";
		}
		Employee employee = EmpService.getEmpById(id);
		model.addAttribute("employee", employee);
		model.addAttribute("dlist", deptService.getAllDept());
		return "editemp";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/updateemp")
	public String updateEmployee(@ModelAttribute Employee employee,
	                             @RequestParam(value = "file", required = false) MultipartFile file,
	                             HttpSession session) {

		if (session.getAttribute("loggedInUser") == null) {
			return "login";
		}

		Employee existing = EmpService.getEmpById(employee.getId());

		if (existing != null) {
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
				try {
					Files.createDirectories(uploadDir);

					String sanitizedName = existing.getFname()
							.toLowerCase()
							.replaceAll("[^a-z0-9]", "_");
					String fileName = sanitizedName + ".jpg";

					Path uploadPath = uploadDir.resolve(fileName);
					System.out.println("Saving file to: " + uploadPath.toAbsolutePath());
					Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
					existing.setPhoto(fileName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			EmpService.updateEmp(existing);
		}

		return "redirect:/emplist";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/deleteemp/{id}")
	public String deleteDepartment(@PathVariable int id) {
		EmpService.deleteEmp(id);
		return "redirect:/emplist";
	}

}

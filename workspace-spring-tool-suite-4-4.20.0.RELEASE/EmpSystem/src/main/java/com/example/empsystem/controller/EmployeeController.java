package com.example.empsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.empsystem.model.Department;
import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.DepartmentRepository;
import com.example.empsystem.service.DepartmentService;
import com.example.empsystem.service.EmployeeService;

import jakarta.servlet.http.HttpSession;



@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService  EmpService;
	
	@Autowired
	private DepartmentService deptService;
	
	@GetMapping("/addemp")
    public String openAddEmpPage(Model model, HttpSession session) {	
	
		if(session.getAttribute("loggedInUser") == null) {
			
			return "login";
		}
       model.addAttribute("employee", new Employee());
        return "addemp";
    }

	@PostMapping("/addemp")
	public String addemp(@ModelAttribute Employee emp, Model model) {
		
		EmpService.addEmp(emp);
		
		model.addAttribute("msg", "Employee added success");
		model.addAttribute("dlist", deptService.getAllDept());
	    return "addemp";
			 }
	
	
	@GetMapping("/emplist")
	public String getallemp(Model model, HttpSession session) {
	
		if(session.getAttribute("loggedInUser") == null) {
			
			return "login";
		}
	    model.addAttribute("emplist", EmpService.getAllEmp());

	    return "emplist";
	} 
	
	@GetMapping("/editemp/{id}")
	public String editEmp(@PathVariable Long id, Model model) {
         
	    Employee employee = EmpService.getEmpById(id);
	    model.addAttribute("employee", employee);

	    return "editemp";
	}
	
	
	
	@PostMapping("/updateemp")
	public String updateEmployee(@ModelAttribute Employee employee) {

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

	        // Address
	        existing.setAddress(employee.getAddress());

	        // Departments
	        existing.setDepartments(employee.getDepartments());

	     

	        EmpService.updateEmp(existing);
	    }

	    return "emplist";
	}
	
	
    @GetMapping("/deleteemp/{id}")
    public String deleteDepartment(@PathVariable int id) {

        EmpService.deleteEmp(id);

        return "redirect:/emplist";
    }
	
}
 
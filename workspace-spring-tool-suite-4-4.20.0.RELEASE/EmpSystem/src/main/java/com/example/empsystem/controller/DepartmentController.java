package com.example.empsystem.controller;

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

import com.example.empsystem.model.Department;
import com.example.empsystem.service.DepartmentService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class DepartmentController {
	

    @Autowired
    private DepartmentService departmentService;
	
	@GetMapping("/add-department")
	public String getdepartment(HttpSession session ) {
	if(session.getAttribute("loggedInUser") == null) {
			
			return "login";
		}
	    return "addepartment";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add-department")
	public String postdepartment(@ModelAttribute Department department, Model model) {
   
	departmentService.addDept(department);
	
	model.addAttribute("msg", "Department added success");
    return "addepartment";
		 }
	
	
	  @GetMapping("/departmentlist")
	   public String list(Model model, HttpSession session) {
			if(session.getAttribute("loggedInUser") == null) {
				
				return "redirect:/login";
			}
		    						  
		  model.addAttribute("deptList", departmentService.getAllDept());
	     return "departmentlist";
	    }
	
	
	  @PreAuthorize("hasRole('ADMIN')")
	  @GetMapping("/editdepartment/{id}")
	    public String editDepartment(@PathVariable int id, Model model) {

	        Department department = departmentService.getDeptById(id);

	        model.addAttribute("department", department);

	        return "editdepartment";
	    }
	

	  @PreAuthorize("hasRole('ADMIN')")
	    @PostMapping("/updatedepartment")
	    public String updateDepartment(@ModelAttribute Department department, Model model) {

	        Department existing = departmentService.getDeptById(department.getId());

	        if (existing != null) {
	            existing.setDeptName(department.getDeptName());
	            existing.setHod(department.getHod());
	            existing.setPhone(department.getPhone());

	            departmentService.updateDept(existing);
	        }
	        return "redirect:/departmentlist";
	    }
	    
	    
	  @PreAuthorize("hasRole('ADMIN')")
	    @GetMapping("/deleteDepartment/{id}")
	    public String deleteDepartment(@PathVariable int id) {

	        departmentService.deleteDept(id);

	        return "redirect:/departmentlist";
	    }

}

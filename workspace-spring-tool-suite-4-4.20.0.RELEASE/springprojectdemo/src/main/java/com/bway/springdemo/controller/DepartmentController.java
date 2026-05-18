 package com.bway.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;


import com.bway.springdemo.model.Department;
import com.bway.springdemo.service.DepartmentService;
import com.bway.springdemo.utils.DepartmentExcelView;
import com.bway.springdemo.utils.DepartmentPdfView;
import jakarta.servlet.http.HttpSession;


@Controller
public class DepartmentController {

	
	@Autowired
	private DepartmentService departmentService;
	

	
	@GetMapping("/department")
	public String getdepartment(HttpSession session) {

	    if (session.getAttribute("activeuser") == null) {

	        return "login";
	    }

	    return "department";
	}
	
	@GetMapping("/add-department")
	public String postdepartment(HttpSession session) { 
	
		  if (session.getAttribute("activeuser") == null) {

		        return "login";
		    }		
		return "adddepartment";
	}
	
	
	@PostMapping("/add-department")
	public String postdepartment(@ModelAttribute Department department, Model model) {
   
	departmentService.addDept(department);
	
	model.addAttribute("msg", "Department added success");
    return "adddepartment";
		 }
	
		  
	  @GetMapping("/departmentlist")
	   public String list(Model model, HttpSession session) {
	    
		  if (session.getAttribute("activeuser") == null) {

		        return "login";
		    }		
		  
		  model.addAttribute("deptList", departmentService.getAllDept());
	     return "departmentlist";
	    }
	
	  
	  
	    @GetMapping("/editdepartment/{id}")
	    public String editDepartment(@PathVariable int id, Model model) {

	        Department department = departmentService.getDeptById(id);

	        model.addAttribute("department", department);

	        return "editdepartment";
	    }
	

	    
	    @PostMapping("/updatedepartment")
	    public String updateDepartment(@ModelAttribute Department department, Model model) {

	        Department existing = departmentService.getDeptById(department.getId());

	        if (existing != null) {
	            existing.setName(department.getName());
	            existing.setHod(department.getHod());
	            existing.setPhone(department.getPhone());

	            departmentService.updateDept(existing);
	        }
	        model.addAttribute("msg", "Department updated success");
	        return "departmentlist";
	    }
	    
	    
	    
	    @GetMapping("/deleteDepartment/{id}")
	    public String deleteDepartment(@PathVariable int id) {

	        departmentService.deleteDept(id);

	        return "departmentlist";
	    }
	    
	    @GetMapping("/excel")
	    public ModelAndView getexcel(){
	    	
	    	ModelAndView mv = new ModelAndView();
	    	mv.addObject("dList", departmentService.getAllDept());
	    	mv.setView(new DepartmentExcelView());
	    	
	    	return mv;
	 
	    	
	    }
	    
	    @GetMapping("/pdf")
	    public ModelAndView getpdf(){
	    	
        	ModelAndView mv = new ModelAndView();
	    	mv.addObject("dList", departmentService.getAllDept());
	    	mv.setView(new DepartmentPdfView());
	    	
	    	return mv;
	 
	    	
	    }
	  
	}
	

